package com.example.sigizi.data.network

import ConsultationData
import PredictionResponse
import com.example.sigizi.data.response.ApiResponse
import com.example.sigizi.data.response.Article
import com.example.sigizi.data.response.ArticleResponse
import com.example.sigizi.data.response.CommentRequest
import com.example.sigizi.data.response.CreateArticleRequest
import com.example.sigizi.data.response.CreateForumRequest
import com.example.sigizi.data.response.Forum
import com.example.sigizi.data.response.ForumApiResponse
import com.example.sigizi.data.response.ForumResponse
import com.example.sigizi.data.response.LoginRequest
import com.example.sigizi.data.response.LoginResponse
import com.example.sigizi.data.response.RegisterRequest
import com.example.sigizi.data.response.RegisterResponse
import com.example.sigizi.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("articles")
    suspend fun createArticle(
        @Header("Authorization") token: String,
        @Body request: CreateArticleRequest
    ): Response<ArticleResponse>

    @Multipart
    @POST("articles/{articleID}/upload")
    suspend fun uploadArticleImage(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String,
        @Part file: MultipartBody.Part
    ): Response<ArticleResponse>

    @DELETE("articles/{articleID}")
    suspend fun deleteArticle(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String
    ): Response<ArticleResponse>

    @GET("articles")
    suspend fun getAllArticles(): Response<ApiResponse<List<Article>>>

    @GET("articles/{articleID}")
    suspend fun getArticleById(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String
    ): Response<ArticleResponse>


    @POST("articles")
    suspend fun createForum(
        @Header("Authorization") token: String,
        @Body request: CreateForumRequest
    ): Response<ForumResponse>

    @Multipart
    @POST("articles/{articleID}/upload")
    suspend fun uploadForumImage(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String,
        @Part file: MultipartBody.Part
    ): Response<ForumResponse>

    @DELETE("articles/{articleID}")
    suspend fun deleteForum(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String
    ): Response<ForumResponse>

    @GET("articles")
    suspend fun getAllForum(): Response<ForumApiResponse<List<Forum>>>

    @GET("articles/{articleID}")
    suspend fun getForumById(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String
    ): Response<ForumResponse>

    @POST("articles/{articleID}/comments")
    suspend fun postComment(
        @Header("Authorization") token: String,
        @Path("articleID") articleID: String,
        @Body request: CommentRequest
    ): Response<ForumApiResponse<ForumResponse>>

    @DELETE("comments/{commentID}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("commentID") commentID: String
    ): Response<ForumApiResponse<Any>>

    @POST("predict")
    suspend fun predictConsultation(@Body consultationData: ConsultationData): Response<PredictionResponse>


    // Tambahkan endpoint ini
    @GET("users")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): Response<UserResponse>


    @PUT("users/{userID}")
    suspend fun updateUserProfile(
        @Header("Authorization") token: String,
        @Body user: UserResponse
    ): Response<UserResponse>
}
