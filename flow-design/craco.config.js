const path = require('path');

module.exports = {
    webpack: {
        alias: {
            '@': path.resolve(__dirname, 'src'),
        }
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8090',
                changeOrigin: true,
                onProxyReq: (proxyReq, req, res) => {
                    console.log('Proxying request:', req.url);
                },
            },
            '/user': {
                target: 'http://localhost:8090',
                changeOrigin: true,
                onProxyReq: (proxyReq, req, res) => {
                    console.log('Proxying request:', req.url);
                },
            },
            '/oss': {
                target: 'http://localhost:8090',
                changeOrigin: true,
                onProxyReq: (proxyReq, req, res) => {
                    console.log('Proxying request:', req.url);
                },
            },
        },
    }
};
