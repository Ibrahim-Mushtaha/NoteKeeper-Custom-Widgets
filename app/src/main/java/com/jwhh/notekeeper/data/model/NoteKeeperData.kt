package com.jwhh.notekeeper.data.model

data class CourseInfo (val courseId: String, val title: String) {
    override fun toString(): String {
        return title
    }
}

data class NoteInfo(var course: CourseInfo? = null, var title: String = "", var text: String? = null, var comments: ArrayList<NoteComment> = ArrayList())

data class NoteComment(var name: String?, var comment: String, var timestamp: Long)



