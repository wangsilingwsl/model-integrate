# model-integrate

Java integrates popular large model apis

# 修改模型实现类中的密钥信息

注意：不仅限于以下模型，还有其他模型，需要根据自己的模型信息进行修改。

各模型官网地址可参考实现类中的注释。
```java
public class ErnieBotServiceImpl implements ModelService {

    private String appSecret = "填入你的appSecret";

    private String apiKey = "填入你的apiKey";
}
```   

## POST 发起大模型请求

POST http://localhost:8080/llm/middle/chat-message

> Body 请求参数

```json
{
  "params": {
    "maxTokens": 1000,
    "topP": 0.5,
    "temperature": 0.5,
    "presencePenalty": 0.5,
    "frequencyPenalty": 0.5
  },
  "messages": [
    {
      "role": "user",
      "content": "你好，我叫张三，应聘Web前端开发工程师"
    },
    {
      "role": "assistant",
      "content": "好的，张三，非常感谢你来到我们公司面试。首先请你简要介绍一下你的技能和工作经验，以便我们更好地了解你的背景和能力。"
    },
    {
      "role": "user",
      "content": "我工作三年了，熟悉前端主流框架Vue、React等"
    }
  ],
  "system": "你将扮演一个科技公司的面试官，考察用户作为候选人的 Web 前端开发水平，提出 5-10 个犀利的技术问题。请注意：- 每次只问一个问题- 用户回答问题后请直接问下一个问题，而不要试图纠正候选人的错误；- 如果你认为用户连续几次回答的都不对，就少问一点；- 问完最后一个问题后，你可以问这样一个问题：上一份工作为什么离职？用户回答该问题后，请表示理解与支持。"
}
```

### 请求参数

|名称|位置|类型| 必选                    |说明|
|---|---|---|-----------------------|---|
|modelType|query|string| 是                     |none|
|accessToken|query|string| 否                     |none|
|X-Access-Token|header|string| 否                     |none|
|body|body|object| 否                     |none|
|» params|body|object| 是（根据各模型自定义参数，可参考官网文档） |none|
|»» maxTokens|body|integer| 否                     |none|
|»» topP|body|number| 否                     |none|
|»» temperature|body|number| 否                     |none|
|»» presencePenalty|body|number| 否                     |none|
|»» frequencyPenalty|body|number| 否                     |none|
|» messages|body|[object]| 是                     |none|
|»» role|body|string| 是                     |none|
|»» content|body|string| 是                     |none|
|» system|body|string| 否                     |none|

> 返回示例

> 成功

```json
{
  "result": "很好，那你能否描述一下Vue和React的主要区别，以及你在项目中如何选择使用它们？"
}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|成功|Inline|

### 返回数据结构

状态码 **200**

|名称|类型|必选|约束|中文名|说明|
|---|---|---|---|---|---|
|» result|string|true|none||none|
