<template>
  <div class="intro">
    <div class="banner">
      <div class="banner-wrapper wrapper">
        <p class="logo-text">#CODE996</p>
        <p class="p2">
          code996 是一个命令行脚本，它可以统计 Git 项目的 commit 时间分布，进而推导出这个项目的工作强度。
        </p>
        <span class="btn" @click="previewDemo">查看示例结果</span>
      </div>
    </div>
    <div class="main wrapper">
      <div class="item">
        <div class="left">
          <div class="icon-mark">?</div>
        </div>
        <article class="markdown-body">
          <div class="p1">用途</div>
          <ul>
            <li>分析 Git 项目的 commit 提交时间数据</li>
            <li>入职新公司后了解项目是否存在 996 现象</li>
            <li>在上班的摸鱼时光找点乐子</li>
            <!-- <li class="todo">判断一个项目中到底谁是卷王</li> -->
          </ul>
        </article>
      </div>
      <div class="item">
        <div class="left">
          <div class="icon-mark">></div>
        </div>
        <article class="markdown-body">
          <div class="p1">如何使用</div>
          <ul>
            <p>Mac 或 Linux 用户，在 Git 项目的根目录，执行以下命令：</p>
            <div class="overflow-x">
              <pre>
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/hellodigua/code996/master/bin/code996.sh)"</pre
              >
            </div>
            <p>
              Windows用户，
              <a href="https://raw.githubusercontent.com/hellodigua/code996/master/bin/code996.sh">下载该脚本</a>
              后，然后将该脚本移至要分析的 Git 项目目录，并执行以下命令：
            </p>
            <pre>sh code996.sh</pre>
          </ul>
        </article>
      </div>
      <div class="item">
        <div class="left">
          <div class="icon-mark">#</div>
        </div>
        <article class="markdown-body">
          <div class="p1">它怎样工作</div>
          <p>1. 使用 git-log 对项目当前的分支进行查询，得到以小时汇总和以天汇总的 commit 统计结果</p>
          <p>2. 将本地脚本得到的查询结果转为 URL 参数，并打开 URL 到浏览器</p>
          <p>3. 从 URL 拿到数据，并使用一些规则处理，并将结果可视化展现</p>
        </article>
      </div>
      <div class="item">
        <div class="left">
          <div class="icon-mark">@</div>
        </div>
        <article class="markdown-body">
          <div class="p1">它安全吗</div>
          <p>1. 脚本端和 Web 端均不会收集任何数据</p>
          <p>2. 除分析的起始时间、commit结果等通用数据外，URL 本身不泄露如项目名等敏感信息</p>
          <p>
            3. 所有代码均已开源到
            <a href="https://github.com/hellodigua/code996" target="_blank">github</a>
            ，接受社区监督
          </p>
        </article>
      </div>
      <div class="item">
        <div class="left">
          <div class="icon-mark">Q</div>
        </div>
        <article class="markdown-body">
          <div class="p1">其他Question</div>
          <p class="p2">Q：996 指数是什么？</p>
          <ul>
            <li>996 指数是本项目定义的，用于反映项目加班情况的数据指标。</li>
            <li>在对加班情况标准化之后，我们可以方便的对跨团队、跨公司项目的工作强度进行对比。</li>
          </ul>
          <p class="p2">Q：什么情况下 code996 的分析结果不准确？</p>
          <ul>
            <li>
              脚本默认统计的是项目整体的提交时间，代表了项目中的所有参与者在这段时间的工作状态，可能与个人的实际情况有偏差
            </li>
            <li>除了 coding，我们还需要开会、写文档、学习、摸鱼等，因此它也无法覆盖实际的工作时间</li>
            <li>跨国、跨时区开发的项目无法正确统计</li>
            <li>工作时间不固定的项目(如个人开源项目)也无法统计</li>
          </ul>
          <p class="p2">Q：我有更好的 idea</p>
          <ul>
            <li>任何想法和意见都欢迎交流，也欢迎 fork 之后自行改造。</li>
          </ul>
        </article>
      </div>
    </div>
    <common-footer />
  </div>
</template>
<script setup lang="ts">
import { router } from '../../router'
import CommonFooter from '../../public/components/CommonFooter.vue'

const previewDemo = () => {
  router.push({
    name: 'result',
    query: {
      time: '2021-01-01_2022-01-01',
      hour: '5_08,19_09,44_10,51_11,7_13,63_14,71_15,49_16,75_17,34_18,15_19,4_20,1_21,1_22',
      week: '50_1,119_2,108_3,96_4,65_5,1_6',
    },
  })
}
</script>
<style lang="scss" scoped></style>
