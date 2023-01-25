package com.rnbiometrics;

public interface SimplePromptResultCallback {
  void onSuccess();
  void onFailure(String message);
  void onCancel();
  void onError(String code, String error);
}
