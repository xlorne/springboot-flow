# Springboot-Flow

## 框架设计

核心的对象有，针对Trigger、Matcher、Creator等对象采用groovy脚本来实现，可以存储到数据库，并用在代码中处理。

----------------------------
FlowWork
流程的设计，约定流程的节点配置与配置

关键属性：
id: 流程的设计id
title: 流程的标题
description: 流程的描述
createUserId: 流程的设计者id
createTime: 流程的创建时间
updateTime: 流程的更新时间
enable: 流程是否启用
lock: 流程是否锁定,锁定流程将无法发起新的流程，当前存在的流程不受影响
node: 发起节点 FLowNode类型
schema: 界面设计脚本

----------------------------

FLowNode
流程节点，约定流程的节点执行逻辑
流程的节点类型：会签、非会签，会签代表所有审批人都需要审批通过，非会签代表只需要一个审批人审批通过即可
流程的出口配置，约定流程的出口配置
流程的操作者配置，约定流程的操作者配置

关键属性:
id: 节点的id
workId: 节点所属的流程设计id
name: 节点的名称
titleCreator: 自定义标题，根据当前节点的配置设置自定义的标题 ，默认标题为 流程名称-节点名称-审批人名称
type: 节点的类型 | 分为发起、审批、结束
view: 节点的视图，界面展示的视图
flowType: 流程审批类型 | 分为会签、非会签
operatorMatcherContext: 操作者配置 
outTrigger: 出口配置，约定出口的条件配置
next: 下一个节点数组，系统将根据出口配置，选择下一个节点
createTime: 创建时间
updateTime: 更新时间
createUserId: 设计者id
bindDataId: 绑定数据的id
errTrigger: 异常触发器，当流程发生异常时异常通常是指找不到审批人，将会触发异常触发器，异常触发器可以是一个节点

----------------------------

FlowRecord
流程记录，记录流程的执行记录。数据中有关键的三个id，分别是流程设计id、节点id、流程id、流程记录id

流程设计id是对应FlowWork的id
节点id是对应FlowNode的id
流程id是在每个流程发起以后生成的一个id
流程记录id是流程在执行过程中的一个记录id

id: 流程记录id
workId: 节点所属的流程设计id
processId: 流程id
nodeId: 节点id
title: 流程标题
operatorId: 操作者id 
nodeStatus: 节点状态 | 待办、已办、专办
createTime: 创建时间
createOperatorId: 发起者id 
opinion: 审批意见
flowStatus: 流程状态 ｜ 进行中、已完成
state: 记录状态 | 正常、异常，当流程发生异常时，将会记录异常状态，异常状态的流程将无法继续审批
errMessage: 异常数据信息
bindDataSnapshotId: 绑定数据的快照id

----------------------------

IBindData
绑定数据接口，流程设计中的数据绑定，可以是任何数据，比如请假单、报销单等等

关键属性：
id: 数据id
toJsonSnapshot(): 返回数据的json快照

----------------------------

BindDataSnapshot
数据快照，记录数据的快照，用于流程的审批记录

关键属性：
id: 数据快照id
dataId: 数据id
snapshot: 数据快照

----------------------------

ITitleCreator
标题创建器，用于创建标题，根据当前节点的配置设置自定义的标题 ，默认标题为 流程名称-节点名称-审批人名称

关键属性：
createTitle(FlowRecord record): 创建标题

----------------------------

IFlowOperator
操作者，流程的操作者，只要实现这个接口，就可以作为流程的操作者
关键属性:
id: 操作者id
name: 操作者名称

----------------------------

IOperatorMatcher
操作者匹配器，用于匹配操作者，根据流程的设计，匹配操作者， 该匹配起主要用在发起节点时判断哪些人员可以发起该流程，当流程发起以后下一节点的操作者将会明确的，因此在发起以后查询代办时直接获取流程记录中的操作者即可。

关键属性：
match(IFlowOperator operator): 匹配操作者

----------------------------

IErrTrigger
异常触发器，当流程发生异常时，将会触发异常触发器，异常触发器可以是一个节点

关键属性：
trigger(FlowRecord record): 触发异常

----------------------------

IOutTrigger
出口触发器，当点击提交流程时根据触发器的规则来判断下一个节点

关键属性
trigger(FlowRecord record): 触发出口
