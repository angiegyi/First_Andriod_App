package com.example.week1.provider;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import android.text.Editable;
@Entity(tableName = "items")
public class item {

    public static final String TABLE_NAME = "items";

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemId")
    private int id;

    @ColumnInfo(name = "itemName")
    private String itemName;

    @ColumnInfo(name = "itemQuantity")
    private String quantity;

    @ColumnInfo(name = "itemCost")
    private String cost;

    @ColumnInfo(name = "itemDescription")
    private String description;

    @ColumnInfo(name = "itemFrozen")
    private String frozen;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public item(String itemName,String quantity,String cost,String description, String frozen){
        setItemName(itemName);
        setQuantity(quantity);
        setCost(cost);
        setDescription(description);
        setFrozen(frozen);
    }

}
