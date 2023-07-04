package pl.kurs.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UnauthorizedException extends IllegalStateException {
    @Override
    public String getMessage() {
        return ("UNAUTHORIZED");
    }

}
