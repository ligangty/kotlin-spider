package org.ligangty.kotlin.spider

import org.apache.commons.io.IOUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

fun connect(url: String): String {
    val builder = StringBuilder()
    IOUtils.readLines(BufferedReader(InputStreamReader(URL(url).openStream()))).forEach { s -> builder.append(s) }
    return builder.toString()
}

fun formatHtml(html: String): String {
    val builder = StringBuilder()
    html.split(">").forEach({ s -> builder.append(s).append(">\n") })
    return builder.delete(builder.length - 2, builder.length).toString()
}

fun fetchLinks(html: String): List<String> {
    val links = ArrayList<String>()
    val anchorReg = Regex("""<a.*?href=["|'](https{0,1}://.*?)["|'].*?>.*?</a>""", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))
    val result = anchorReg.findAll(html)
    result.forEach { s -> links.add(s.groupValues[1]) }
    return links
}

fun main(args: Array<String>) {
    val html = connect("https://www.sina.com.cn")
//    println(formatHtml(html))

    val links = fetchLinks(html)
    links.forEach { s -> println(s) }

}

