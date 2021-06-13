package com.example.mysearchphotosapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysearchphotosapp.api.UnsplashApi
import com.example.mysearchphotosapp.models.UnsplashPhoto
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(
    private val unsplashApi: UnsplashApi,
    private val query: String): PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: 1
        return try {
            val response = unsplashApi.searchPhotos(
                query = query,
                page = position,
                perPage = params.loadSize
            )
            val photos = response.results
            LoadResult.Page(
                data = photos,
                prevKey = if(position == 1) null else position - 1,
                nextKey = if(photos.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UnsplashPhoto>): Int? {
        return state.anchorPosition
    }


}