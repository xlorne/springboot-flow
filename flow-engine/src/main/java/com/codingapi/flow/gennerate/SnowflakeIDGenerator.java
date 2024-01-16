package com.codingapi.flow.gennerate;

import lombok.extern.slf4j.Slf4j;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;

/**
 * Generate unique IDs using the Twitter Snowflake algorithm (see https://github.com/twitter/snowflake). Snowflake IDs
 * are 64 bit positive longs composed of:
 * - 41 bits time stamp
 * - 10 bits machine id
 * - 12 bits sequence number
 *
 *
 * @author Sebastian Schaffert (sschaffert@apache.org)
 */
@Slf4j
public class SnowflakeIDGenerator  {

    private final long datacenterIdBits = 10L;

    private long datacenterId;

    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;


    public SnowflakeIDGenerator(long datacenterId)  {
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if(datacenterId == 0) {
            try {
                this.datacenterId = getDatacenterId();
            } catch (SocketException | UnknownHostException | NullPointerException e) {
                log.warn("SNOWFLAKE: could not determine machine address; using random datacenter ID");
                Random rnd = new Random();
                this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
            }
        } else {
            this.datacenterId = datacenterId;
        }

        if (this.datacenterId > maxDatacenterId || datacenterId < 0){
            log.warn("SNOWFLAKE: datacenterId > maxDatacenterId; using random datacenter ID");
            Random rnd = new Random();
            this.datacenterId = rnd.nextInt((int) maxDatacenterId) + 1;
        }
        log.info("SNOWFLAKE: initialised with datacenter ID {}", this.datacenterId);
    }

    protected long tilNextMillis(long lastTimestamp){
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    protected long getDatacenterId() throws SocketException, UnknownHostException {
        NetworkInterface network = null;

        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface nint = en.nextElement();
            if (!nint.isLoopback() && nint.getHardwareAddress() != null) {
                network = nint;
                break;
            }
        }

        assert network != null;
        byte[] mac = network.getHardwareAddress();

        Random rnd = new Random();
        byte rndByte = (byte)(rnd.nextInt() & 0x000000FF);

        // take the last byte of the MAC address and a random byte as datacenter ID
        return ((0x000000FF & (long)mac[mac.length-1]) | (0x0000FF00 & (((long)rndByte)<<8)))>>6;
    }


    /**
     * Return the next unique id for the type with the given name using the generator's id generation strategy.
     *
     */
    public synchronized long getId() {
        long timestamp = System.currentTimeMillis();
        if(timestamp<lastTimestamp) {
            log.warn("Clock moved backwards. Refusing to generate id for {} milliseconds.",(lastTimestamp - timestamp));
            try {
                Thread.sleep((lastTimestamp - timestamp));
            } catch (InterruptedException e) {
            }
        }
        long sequenceBits = 12L;
        if (lastTimestamp == timestamp) {
            long sequenceMask = ~(-1L << sequenceBits);
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        long timestampLeftShift = sequenceBits + datacenterIdBits;
        long twepoch = 1288834974657L;
        long id = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << sequenceBits) | sequence;

        if(id < 0) {
            log.warn("ID is smaller than 0: {}",id);
        }
        return id;
    }
}
