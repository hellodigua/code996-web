# code996-web

code996 网页版，用于查看 code996 脚本的分析结果。

当前项目是 1.0 版本，功能比较简单，新的 2.0 版本请参考 [code996](https://github.com/hellodigua/code996)

Preview：https://hellodigua.github.io/code996/

简体中文 | [English](./README-en_US.md)

## 预览

分析 Git 项目的基本情况：

![basic](./public/preview/1.png)

通过图表查看 commit 提交分布：

![chart](./public/preview/2.png)

对比项目工作时间类型：

![reference](./public/preview/3.png)

## 如何使用

**本地分析**：在 Git 项目的根目录，执行以下脚本：

```sh
curl -fsSL https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996.sh | bash
```

**在线分析**：gitlab 项目可以使用浏览器用户脚本 `userscript.js` 进行在线分析

<details>
<summary>具体使用方法请展开查看</summary>

### Gitlab 线上版本

- 安装浏览器插件 tampermonkey https://www.tampermonkey.net/
- 拷贝 userscript.js 文件到 tampermonkey（或选择[在线安装](https://greasyfork.org/en/scripts/452007-gitlab-996-index-statistic)）
- 打开需要分析的 gitlab 项目页面，点击按钮可得到分析结果

<img width="760" alt="image" src="https://user-images.githubusercontent.com/14119632/191984080-8f6b693f-824b-4271-aa43-2511fa57d365.png">

### PowerShell 版本

> 请使用 PowerShell 7 或更高版本

```sh
iwr https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996.ps1 -OutFile ([System.IO.Path]::Combine([System.IO.Path]::GetTempPath(), 'code996.ps1')); & ([System.IO.Path]::Combine([System.IO.Path]::GetTempPath(), 'code996.ps1')); ri ([System.IO.Path]::Combine([System.IO.Path]::GetTempPath(), 'code996.ps1'))
```

</details>

## 它怎样工作

1. 使用 git-log 对项目**当前的分支**进行查询，得到以小时汇总和以天汇总的 commit 统计结果
2. 将本地脚本得到的查询结果转为 URL 参数，并打开 URL 到浏览器
3. 从 URL 拿到数据，并使用一些规则处理，并将结果可视化展现

## 它有什么用

它可以帮助你分辨 996 的公司和行为并远离它，具体包括：

1. 在入职的当天即可知道新公司的加班情况如何
2. 揪出公司里的卷王
3. 对比不同项目的加班强度

项目起因于 [这篇帖子](https://v2ex.com/t/806367)

## 它安全吗

1. 脚本端和 Web 端均不会收集任何数据
2. 除分析的起始时间、commit 统计结果等通用数据外，URL 本身不泄露如项目名等敏感信息
3. 所有代码均已开源，接受社区监督

## 其他 Question

### Q：996 指数是什么？

996 指数是本项目定义的，用于反映项目加班情况的数据指标。

在对加班情况标准化之后，我们可以方便的对跨团队、跨公司项目的工作强度进行对比。

### Q：什么情况下它的分析结果不准确？

- 脚本默认统计的是项目整体的提交时间，代表了项目中的所有参与者在这段时间的工作状态，可能与个人的实际情况有偏差
- 除了 coding，我们还需要开会、写文档、学习、摸鱼等，因此它也无法覆盖实际的工作时间
- 跨国、跨时区开发的项目无法正确统计
- 工作时间不固定的项目(如个人开源项目)也无法统计

## 注意事项

- 分析结果仅供参考，不构成任何建议
- 原始分析数据通过 URL 传输，如需分享，请慎重
- 项目仅供学习与交流，code996 不对使用、传播本程序及附属产物造成的任何后果承担任何责任

## 贡献者

感谢 [YLinXin](https://github.com/YLinXin) | [Nekotora](https://flag.moe/) | [Xuemuyang](https://github.com/Xuemuyang) 等小伙伴对本项目的贡献，希望这个项目能帮助到不希望加班的开发者们。

如果有问题欢迎在 issue 提出，或通过邮件联系我。

## 使用许可

本程序及其源码和编译产物附属 [MIT License](LICENSE) 许可，其依赖的相关库和内容不做许可承诺，请参考他们的原始许可。

![license](https://img.shields.io/github/license/hellodigua/code996)
![](https://img.shields.io/jsdelivr/gh/hm/hellodigua/code996)
![views](https://us-central1-trackgit-analytics.cloudfunctions.net/token/ping/l2vhuyzttoxl2nx0wzl2)
