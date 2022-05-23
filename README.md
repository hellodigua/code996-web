# code996

code996 是一个分析工具，它可以统计 Git 项目的 commit 时间分布，进而推导出这个项目的编码工作强度。

Preview：https://hellodigua.github.io/code996/

简体中文 | [English](./README-en_US.md)

## 用途

- 分析 Git 项目的 commit 提交时间数据
- 入职新公司后了解项目是否存在 996 现象
- 在上班的摸鱼时光找点乐子

## 如何使用

Mac 或 Linux 用户：

**在 Git 项目的根目录**，执行以下命令：

```sh
/bin/bash -c "$(curl -fsSL https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996.sh)"
```

Windows 用户：

下载该脚本 `https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996.sh`

然后将该脚本移至要分析的 Git 项目目录，并执行以下命令：

```sh
sh code996.sh
```

## 预览

分析项目的基本情况：

![basic](./public/preview/1.png)

通过图表查看 commit 提交分布：

![chart](./public/preview/2.png)

对比项目工作时间类型：

![reference](./public/preview/3.png)

## 它怎样工作

1. 使用 git-log 对项目**当前的分支**进行查询，得到以小时汇总和以天汇总的 commit 统计结果
2. 将本地脚本得到的查询结果转为 URL 参数，并打开 URL 到浏览器
3. 从 URL 拿到数据，并使用一些规则处理，并将结果可视化展现

## 它安全吗

1. 脚本端和 Web 端均不会收集任何数据
2. 除分析的起始时间、commit 统计结果等通用数据外，URL 本身不泄露如项目名等敏感信息
3. 所有代码均已开源，接受社区监督

## 注意事项

- 分析结果仅供参考，不构成任何建议
- 原始分析数据通过 URL 传输，如需分享，请慎重
- 请勿用于正式场合

## 其他 Question

### Q：996 指数是什么？

996 指数是本项目定义的，用于反映项目加班情况的数据指标。

在对加班情况标准化之后，我们可以方便的对跨团队、跨公司项目的工作强度进行对比。

### Q：什么情况下 code996 的分析结果不准确？

- 脚本默认统计的是项目整体的提交时间，代表了项目中的所有参与者在这段时间的工作状态，可能与个人的实际情况有偏差
- 除了 coding，我们还需要开会、写文档、学习、摸鱼等，因此它也无法覆盖实际的工作时间
- 跨国、跨时区开发的项目无法正确统计
- 工作时间不固定的项目(如个人开源项目)也无法统计

## 免责声明

本项目仅供学习与交流，code996 不对使用、传播本程序及附属产物造成的任何后果承担任何责任。

我们列出了如下一些使用后可能发生的后果，请悉知。

- 计算机死机，卡顿，重启
- 蓝屏，白屏，黑屏，闪屏
- 被老板或同事看到你在摸鱼
- 被同事通过 code996 发现你过去在摸鱼
- 泄漏机密信息被安全部门处理
- 上班迟到
- 被毕业
- 股市暴跌
- 地球爆炸

## 贡献者

[YLinXin](https://github.com/YLinXin) | [YLinXin](https://github.com/YLinXin) | [Nekotora](https://flag.moe/) | [Xuemuyang](https://github.com/Xuemuyang)

## 友情链接

我们还写了一份前端面试题小册 [剑指前端 Offer](https://github.com/HZFE/awesome-interview)，它适合以下人群阅读：

- 有意冲刺互联网大厂的前端开发者，可参考本书题目和答案提纲，自主深入学习，查漏补缺。
- 短时间内参加面试的前端开发者，可借助本书快速了解面试高频的技术问题和相关解答。
- 前端面试官可参考本书的题型和题目，按岗位需求对候选人进行有梯度的考察。

欢迎前端小伙伴们 star

## 使用许可

本程序及其源码和编译产物附属 [Unlicense](LICENSE) 许可，其依赖的相关库和内容不做许可承诺，请参考他们的原始许可。
