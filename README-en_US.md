# code996

code996 is an analytics tool that can be used to calculate the commit time distribution of a Git project and derive the coding intensity of that project.

Preview: https://hellodigua.github.io/code996/

English | [简体中文](./README.md)

## How to Use

For Mac or Linux users.

**In the root directory of your Git project**, run the following command.

```sh
curl -fsSL https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996_en.sh | bash
```

Windows users.

Download the script `https://fastly.jsdelivr.net/gh/hellodigua/code996/bin/code996_en.sh`

Then move the script to the Git project directory you want to analyze, and execute the following command.

```sh
sh code996_en.sh
```

## Preview

Basic information about the project to analyze.

![basic](./public/preview/1.png)

View commit distribution by graph: !

![chart](./public/preview/2.png)

Compare project work time types.

![reference](./public/preview/3.png)

## How it works

1. use git-log to query the project **current branches** and get the commit statistics summarized by hour and by day
2. convert the query results from the local script to URL arguments and open the URL to the browser
3. get the data from the URL, process it with some rules, and visualize the results

## Is it safe?

1. neither the script side nor the web side collects any data
2. the URL itself does not disclose sensitive information such as project name, except for common data such as start time of analysis, commit statistics, etc.
3. all code has been open source, accepting community supervision

## Caution

- The analysis results are for reference only and do not constitute any recommendation
- The original analysis data is transmitted via URL, please be careful if you want to share it
- Please do not use it for official purposes

## Other Questions

### Q: What is the 996 Index?

The 996 Index is a data indicator defined by the project to reflect the overtime situation of the project.

After standardizing the overtime situation, we can easily compare the work intensity of projects across teams and companies.

### Q: Under what circumstances are code996's analysis results inaccurate?

- The default statistics of the script is the overall project commit time, which represents the work status of all participants in the project during this time, and may deviate from the actual situation of individuals
- In addition to coding, we also need to meet, write documents, study and fish, so it can't cover the actual working time either
- Projects developed across borders and time zones cannot be counted correctly
- Projects with irregular working hours (e.g. personal open source projects) can't be counted either

### Q: I have a better idea

Any ideas and comments are welcome to share, and you are welcome to modify it after Fork.

## Disclaimer

This project is for learning and communication only, code996 does not assume any responsibility for any consequences caused by the use and dissemination of this program and its ancillary products.

We have listed below some of the possible consequences of using this program, please be aware of them.

- Computer crash, lag, reboot
- Blue screen, white screen, black screen, splash screen
- Your boss or colleague sees you groping for fish
- You are found by your colleague through code996 that you are fishing in the past
- Leak confidential information and be dealt with by security department
- Late for work
- Being graduated
- Stock market crash
- Earth explodes

## License

This program and its source code and compiled products are subject to the [MIT License](LICENSE) license, and its dependencies on related libraries and content are not licensed under promise, please refer to their original licenses.
