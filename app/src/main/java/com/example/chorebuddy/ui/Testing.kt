package com.example.chorebuddy.ui



data class Member(val name: String)

object DataSource {
    private val membersList = mutableListOf<Member>()

    fun addMember(member: Member) {
        membersList.add(member)
    }

    fun getMembers(): List<Member> {
        return membersList.toList()
    }
}
