package com.codingapi.flow.operator;

/**
 * 操作者匹配器，用于匹配操作者，根据流程的设计，匹配操作者， 该匹配起主要用在发起节点时判断哪些人员可以发起该流程，当流程发起以后下一节点的操作者将会明确的，因此在发起以后查询代办时直接获取流程记录中的操作者即可。
 */
public interface IOperatorMatcher {

    /**
     * 匹配操作者
     * @param operator  操作者
     * @return 是否匹配
     */
    boolean matcher(IFlowOperator operator);

}
