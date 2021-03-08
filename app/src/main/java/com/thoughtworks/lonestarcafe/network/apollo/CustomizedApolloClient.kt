package com.thoughtworks.lonestarcafe.network.apollo

import com.apollographql.apollo.ApolloClient

object CustomizedApolloClient {
    private val instance: ApolloClient = ApolloClient
        .builder()
        .serverUrl("https://lone-star-cafe.herokuapp.com/graphql")
        .build()

    val client
        get() = instance
}