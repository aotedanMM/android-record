package com.example.aotedan.network;

import java.io.File;

public interface RequestFile {

	public void success(File file);

	public void error(String error);

	public void onProgress(float progress, long total, int id);
}
