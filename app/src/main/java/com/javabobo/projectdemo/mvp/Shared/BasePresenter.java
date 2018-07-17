package com.javabobo.projectdemo.mvp.Shared;

public abstract class BasePresenter<T extends BaseContract.View>
    implements BaseContract.Presenter<T> {

  protected T view;

  public void setView(T view){
    this.view = view;
  }
}
