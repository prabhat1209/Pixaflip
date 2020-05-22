package com.example.pixaflip.ui.favourite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private String pdfName;
    private String pdfUrl;


    public SlideshowViewModel(String pdfName, String pdfUrl) {
        this.pdfName = pdfName;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }
}