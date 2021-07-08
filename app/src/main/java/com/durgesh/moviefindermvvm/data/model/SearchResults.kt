package com.durgesh.moviefindermvvm.data.model

import com.google.gson.annotations.SerializedName

data class SearchResults(
    val copyright: String = "",
    @SerializedName("has_more")
    val hasMore: Boolean = false,
    @SerializedName("num_results")
    val numResults: Int = 0,
    val results: List<SearchItem> = listOf(),
    val status: String = ""


) {
    data class SearchItem(
        val byline: String = "",
        @SerializedName("critics_pick")
        val criticsPick: Int = 0,
        @SerializedName("date_updated")
        val dateUpdated: String = "",
        @SerializedName("display_title")
        val displayTitle: String = "",
        val headline: String = "",
        val link: Link = Link(),
        @SerializedName("mpaa_rating")
        val mpaaRating: String = "",
        val multimedia: Multimedia = Multimedia(),
        @SerializedName("opening_date")
        val openingDate: String = "",
        @SerializedName("publication_date")
        val publicationDate: String = "",
        @SerializedName("summary_short")
        val summaryShort: String = ""
    ) {
        data class Link(
            @SerializedName("suggested_link_text")
            val suggestedLinkText: String = "",
            val type: String = "",
            val url: String = ""
        )

        data class Multimedia(
            val height: Int = 0,
            val src: String = "",
            val type: String = "",
            val width: Int = 0
        )
    }

}
