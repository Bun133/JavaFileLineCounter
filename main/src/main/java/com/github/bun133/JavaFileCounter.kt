package com.github.bun133

import java.io.BufferedReader
import java.io.File

fun main(args: Array<String>) {
    val path = File(args[0])
    val counter = JavaFileCounter()
    val r = counter.getResult(path)
    println(r.first)
    println("")
    println("All -- ${r.second} Lines")
}

abstract class Counter {
    fun getResult(folder: File): Pair<String,Int> {
        val sb = StringBuilder()
        var count = 0
        sb.append(folder.absolutePath + "\n")
        folder.listFiles()?.forEach {
            if(it.isFile){
                sb.append(getResultString(it))
                count += getResultInt(it)
            }else{
                val r = getResult(it)
                sb.append(r.first)
                count+=r.second
            }
        }

        return Pair(sb.toString(),count)
    }

    fun getResultForFolder(folder:File):String{
        if (folder.exists() && folder.isDirectory) {
            val sb = StringBuilder()
            folder.listFiles()?.forEach {
                sb.append(getResultString(it))
            }
            return sb.toString()
        }else if(folder.isFile){
            return getResultString(folder)
        }

        return "Folder is not exist/it is not folder"
    }

    abstract fun getResultString(file: File): String
    abstract fun getResultInt(file: File):Int
}

class JavaFileCounter() : Counter() {
    override fun getResultString(file: File): String {
        return "${getLines(file)} Lines -- ${file.name} \n"
    }

    override fun getResultInt(file: File): Int {
        return getLines(file).toInt()
    }

    fun getLines(file: File): Long {
        val reader = BufferedReader(file.reader())
        return reader.lines().count()
    }
}