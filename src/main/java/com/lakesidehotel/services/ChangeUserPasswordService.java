package com.lakesidehotel.services;

import com.lakesidehotel.dtos.ResetPasswordDto;
import com.lakesidehotel.responses.PasswordResponse;

public interface ChangeUserPasswordService {
    PasswordResponse updateUserPassword(ResetPasswordDto resetPasswordDto);
}
