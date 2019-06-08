
package com.crinoidtechnologies.mishicreationadmin.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LineItem implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("variation_id")
    @Expose
    private Integer variationId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("subtotal_tax")
    @Expose
    private String subtotalTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum> metaData = null;
    @SerializedName("sku")
    @Expose
    private Object sku;
    @SerializedName("price")
    @Expose
    private Float price;

    protected LineItem(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            productId = null;
        } else {
            productId = in.readInt();
        }
        if (in.readByte() == 0) {
            variationId = null;
        } else {
            variationId = in.readInt();
        }
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        taxClass = in.readString();
        subtotal = in.readString();
        subtotalTax = in.readString();
        total = in.readString();
        totalTax = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readFloat();
        }
    }

    public static final Creator<LineItem> CREATOR = new Creator<LineItem>() {
        @Override
        public LineItem createFromParcel(Parcel in) {
            return new LineItem( in );
        }

        @Override
        public LineItem[] newArray(int size) {
            return new LineItem[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotalTax() {
        return subtotalTax;
    }

    public void setSubtotalTax(String subtotalTax) {
        this.subtotalTax = subtotalTax;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public List<MetaDatum> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
    }

    public Object getSku() {
        return sku;
    }

    public void setSku(Object sku) {
        this.sku = sku;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte( (byte) 0 );
        } else {
            dest.writeByte( (byte) 1 );
            dest.writeInt( id );
        }
        dest.writeString( name );
        if (productId == null) {
            dest.writeByte( (byte) 0 );
        } else {
            dest.writeByte( (byte) 1 );
            dest.writeInt( productId );
        }
        if (variationId == null) {
            dest.writeByte( (byte) 0 );
        } else {
            dest.writeByte( (byte) 1 );
            dest.writeInt( variationId );
        }
        if (quantity == null) {
            dest.writeByte( (byte) 0 );
        } else {
            dest.writeByte( (byte) 1 );
            dest.writeInt( quantity );
        }
        dest.writeString( taxClass );
        dest.writeString( subtotal );
        dest.writeString( subtotalTax );
        dest.writeString( total );
        dest.writeString( totalTax );
        if (price == null) {
            dest.writeByte( (byte) 0 );
        } else {
            dest.writeByte( (byte) 1 );
            dest.writeFloat( price );
        }
    }
}
