package book.library.api.request;

import lombok.Data;

@Data
public class BookWithdrawRequest {

    private String customerCpf;
    private Long bookCopyId;

}
