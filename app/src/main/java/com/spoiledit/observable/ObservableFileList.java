package com.spoiledit.observable;

import com.spoiledit.utils.FileUtils;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Observable;

public class ObservableFileList extends Observable {
    private ArrayList<String> filePaths;

    public ObservableFileList() {
        this.filePaths = new ArrayList<>();
    }

    public ArrayList<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;

        setChanged();
        notifyObservers();
    }

    public String getFilePath(int postion){
        return filePaths.get(postion);
    }

    public String getDisplayTitle(){
        if (filePaths.size() == 0)
            return "No files added.";
        else if ( filePaths.size() == 1)
            return FileUtils.getFileRealName(filePaths.get(0));
        else
            return String.format(Locale.getDefault(), "%d files added.", filePaths.size());
    }

    public void addFilePath(String filePath){
        filePaths.add(filePath);

        setChanged();
        notifyObservers();
    }

    public String removeFilePath(int position){
        String path = filePaths.remove(position);

        setChanged();
        notifyObservers();

        return path;
    }

    public boolean contains(String filePath) {
        return filePaths.contains(filePath);
    }

    public void onDeleted(){
        setChanged();
        notifyObservers();
    }
}
