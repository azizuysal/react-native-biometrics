package com.rnbiometrics;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;

public class SimplePromptCallback extends BiometricPrompt.AuthenticationCallback {
  private final SimplePromptResultCallback callback;

  private int failCount;
  private static final int MAX_FAIL_COUNT = 3;

  public SimplePromptCallback(SimplePromptResultCallback callback) {
    super();
    this.callback = callback;
    this.failCount = 0;
  }

  @Override
  public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
    super.onAuthenticationError(errorCode, errString);
    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON || errorCode == BiometricPrompt.ERROR_USER_CANCELED) {
      this.callback.onCancel();
    } else {
      this.callback.onError(String.valueOf(errorCode), errString.toString());
    }
  }

  @Override
  public void onAuthenticationFailed() {
    super.onAuthenticationFailed();
    this.failCount = this.failCount + 1;
    if (this.failCount >= MAX_FAIL_COUNT) {
      this.failCount = 0;
      this.callback.onFailure("Too many failed attempts");
    }
  }

  @Override
  public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
    super.onAuthenticationSucceeded(result);
    this.callback.onSuccess();
  }
}
