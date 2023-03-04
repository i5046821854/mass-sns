package dev.be.sns.model.Entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T result;  //여러 상황에 따라 타입이 다르기 때문에 제네릭으로

    public static Response<Void> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public static Response<Void> success(){
        return new Response<Void>("suceess", null);
    }

    public static <T>Response<T> success(T result){
        return new Response<>("suceess", result);
    }

    public String toStream() {
        if(result == null) {
            return "{" + "\"resultCode\":" + "\"" + resultCode + "\"," +
             "\"result\":"  + null + "}";
        }
        return "{" + "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"," + "}";
    }
}
