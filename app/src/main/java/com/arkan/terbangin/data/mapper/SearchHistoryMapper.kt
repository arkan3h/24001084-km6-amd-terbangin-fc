package com.arkan.terbangin.data.mapper

import com.arkan.terbangin.data.model.SearchHistory
import com.arkan.terbangin.data.source.local.entity.SearchHistoryEntity
import com.arkan.terbangin.data.source.local.entity.SearchTerminalEntity

fun SearchHistory?.toSearchHistoryEntity() =
    SearchHistoryEntity(
        id = this?.id,
        query = this?.query.orEmpty(),
        timestamp = this?.timestamp ?: 0,
    )

fun SearchHistoryEntity?.toSearchHistory() =
    SearchHistory(
        id = this?.id,
        query = this?.query.orEmpty(),
        timestamp = this?.timestamp ?: 0,
    )

fun SearchHistory?.toSearchTerminalEntity() =
    SearchTerminalEntity(
        id = this?.id,
        query = this?.query.orEmpty(),
        timestamp = this?.timestamp ?: 0,
    )

fun SearchTerminalEntity?.toSearchHistory() =
    SearchHistory(
        id = this?.id,
        query = this?.query.orEmpty(),
        timestamp = this?.timestamp ?: 0,
    )

fun List<SearchHistoryEntity?>.toSearchHistoryList() = this.map { it.toSearchHistory() }

fun List<SearchTerminalEntity?>.toSearchTerminalList() = this.map { it.toSearchHistory() }
