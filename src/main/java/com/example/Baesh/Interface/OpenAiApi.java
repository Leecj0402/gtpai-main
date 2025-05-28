package com.example.Baesh.Interface;

import com.example.Baesh.DTO.FineTune.FineTuneRequest;
import com.example.Baesh.DTO.FineTune.FineTuneResponse;
import com.example.Baesh.DTO.OpenAi.OpenAiFileResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface OpenAiApi {

    @Multipart
    @POST("v1/files")
    Call<OpenAiFileResponse> uploadFile(
            @Header("Authorization") String authHeader,
            @Part MultipartBody.Part file,
            @Part("purpose") RequestBody purpose
    );

    // Fine-tuning 작업 생성 URL 수정
    @POST("v1/fine_tuning/jobs")
    Call<FineTuneResponse> createFineTune(
            @Header("Authorization") String authHeader,
            @Body FineTuneRequest request
    );

    // Fine-tuning 상태 확인 URL 수정
    @GET("v1/fine_tuning/jobs/{fineTuneId}")
    Call<FineTuneResponse> getFineTuneStatus(
            @Header("Authorization") String authHeader,
            @Path("fineTuneId") String fineTuneId
    );
}
