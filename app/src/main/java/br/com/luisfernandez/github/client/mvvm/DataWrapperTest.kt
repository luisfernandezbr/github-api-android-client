package br.com.luisfernandez.github.client.mvvm

import br.com.luisfernandez.github.client.dto.RepoDTO
import br.com.luisfernandez.github.client.dto.UserDTO

class DataWrapperTest {
    fun test() {
        val dataWrapper = DataWrapper<UserDTO, RepoDTO>()
    }
}