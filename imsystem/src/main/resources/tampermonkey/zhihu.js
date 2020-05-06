// ==UserScript==
// @name         test
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://www.zhihu.com/question/*
// @grant        none
// @require      https://code.jquery.com/jquery-1.12.4.min.js
// ==/UserScript==

(function() {
    'use strict';
    // 获取元数据
    console.log("开始获取元数据")
    var question = document.querySelector("#root > div > main > div > div:nth-child(10) > div:nth-child(2) > div > div.QuestionHeader-content > div.QuestionHeader-main > h1").innerText
    var answer_count = document.querySelector("#QuestionAnswers-answers > div > div > div > div.List-header > h4 > span").innerText
    var image_url = document.querySelector("head > link:nth-child(15)").href
    var public_url = window.location.href.split("?")[0]
    var channelId = ''


    // 获取channelId
    console.log("开始获取channelId")
    $.ajax({
        url : "http://localhost:8080/channel/get-public-channel-id",
        type : 'POST',
        data : JSON.stringify({
            channelName:question,
            summary:"",
            picUrl:image_url,
            publicUrl:public_url
        }),
        dataType : 'json',
        contentType:"application/json;charset=UTF-8",
        success : function(data, status, xhr) {
            if(data.code!=0){
                console.log("获取channelId异常")
                console.log(data)
                throw "获取channelId异常"
            }
            channelId = data.data
            console.log(data)
        },
        Error : function(xhr, error, exception) {
            // handle the error.
            console.log(exception.toString());
        }
    });

    // 插入按钮
    console.log("开始插入按钮")
    var button_html = '<button type="button" class="Button Button--grey Button--withIcon Button--withLabel">'
        +'<span style="display: inline-flex; align-items: center;">&#8203;'
        +'<svg class="Zi Zi--Invite Button-zi" fill="currentColor" viewBox="0 0 36 32" width="1.2em" height="1.2em">'
        +'<path d="M34 28.161c0 1.422 0.813 2.653 2 3.256v0.498c-0.332 0.045-0.671 0.070-1.016 0.070-2.125 0-4.042-0.892-5.398-2.321-0.819 0.218-1.688 0.336-2.587 0.336-4.971 0-9-3.582-9-8s4.029-8 9-8c4.971 0 9 3.582 9 8 0 1.73-0.618 3.331-1.667 4.64-0.213 0.463-0.333 0.979-0.333 1.522zM16 0c8.702 0 15.781 5.644 15.995 12.672-1.537-0.685-3.237-1.047-4.995-1.047-2.986 0-5.807 1.045-7.942 2.943-2.214 1.968-3.433 4.607-3.433 7.432 0 1.396 0.298 2.747 0.867 3.993-0.163 0.004-0.327 0.007-0.492 0.007-0.849 0-1.682-0.054-2.495-0.158-3.437 3.437-7.539 4.053-11.505 4.144v-0.841c2.142-1.049 4-2.961 4-5.145 0-0.305-0.024-0.604-0.068-0.897-3.619-2.383-5.932-6.024-5.932-10.103 0-7.18 7.163-13 16-13z" fill-rule="evenodd">'
        +'</path></svg></span>在线讨论</button>'
    var button_pannel = $("#root > div > main > div > div:nth-child(10) > div:nth-child(2) > div > div.QuestionHeader-footer > div > div > div.QuestionButtonGroup")
    // var button_pannel = $("#root > div > main > div > div:nth-child(10) > div:nth-child(2) > div > div.QuestionHeader-footer > div > div > div.QuestionHeaderActions > button.Button.Button--grey.Button--withIcon.Button--withLabel")
    document.querySelector("#root > div > main > div > div:nth-child(10) > div:nth-child(2) > div > div.QuestionHeader-footer > div > div > div.QuestionHeaderActions > button.Button.Button--grey.Button--withIcon.Button--withLabel")
    button_pannel.append(button_html)

    // 绑定事件
    console.log("开始绑定事件")
    // Your code here...
})();