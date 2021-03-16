package com.example.aboutmovies;

public class WatchListItem {
    private int ItemID;
    private String ItemType;


    public WatchListItem() {
    }

    public WatchListItem(int itemID, String itemType) {
        ItemID = itemID;
        ItemType = itemType;
    }

    public String getItemType() {
        return ItemType;
    }

    public void setItemType(String itemType) {
        ItemType = itemType;
    }

    public int getItemID() {
        return ItemID;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }
}
