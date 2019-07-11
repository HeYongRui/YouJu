package com.heyongrui.module2.data.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lambert on 2019/2/12.
 */

public class QingMangCategoriesDto implements Parcelable {

    private boolean ok;
    private boolean hasMore;
    private List<CategoriesBean> categories;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public static class CategoriesBean implements Parcelable {

        private String categoryId;
        private String name;
        private String type;
        private String description;
        private String icon;
        private List<SubCategoriesBean> subCategories;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<SubCategoriesBean> getSubCategories() {
            return subCategories;
        }

        public void setSubCategories(List<SubCategoriesBean> subCategories) {
            this.subCategories = subCategories;
        }

        public static class SubCategoriesBean implements Parcelable {
            private String categoryId;
            private String name;

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.categoryId);
                dest.writeString(this.name);
            }

            public SubCategoriesBean() {
            }

            protected SubCategoriesBean(Parcel in) {
                this.categoryId = in.readString();
                this.name = in.readString();
            }

            public static final Creator<SubCategoriesBean> CREATOR = new Creator<SubCategoriesBean>() {
                @Override
                public SubCategoriesBean createFromParcel(Parcel source) {
                    return new SubCategoriesBean(source);
                }

                @Override
                public SubCategoriesBean[] newArray(int size) {
                    return new SubCategoriesBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.categoryId);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeString(this.description);
            dest.writeString(this.icon);
            dest.writeTypedList(this.subCategories);
        }

        public CategoriesBean() {
        }

        protected CategoriesBean(Parcel in) {
            this.categoryId = in.readString();
            this.name = in.readString();
            this.type = in.readString();
            this.description = in.readString();
            this.icon = in.readString();
            this.subCategories = in.createTypedArrayList(SubCategoriesBean.CREATOR);
        }

        public static final Creator<CategoriesBean> CREATOR = new Creator<CategoriesBean>() {
            @Override
            public CategoriesBean createFromParcel(Parcel source) {
                return new CategoriesBean(source);
            }

            @Override
            public CategoriesBean[] newArray(int size) {
                return new CategoriesBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.ok ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasMore ? (byte) 1 : (byte) 0);
        dest.writeTypedList(this.categories);
    }

    public QingMangCategoriesDto() {
    }

    protected QingMangCategoriesDto(Parcel in) {
        this.ok = in.readByte() != 0;
        this.hasMore = in.readByte() != 0;
        this.categories = in.createTypedArrayList(CategoriesBean.CREATOR);
    }

    public static final Creator<QingMangCategoriesDto> CREATOR = new Creator<QingMangCategoriesDto>() {
        @Override
        public QingMangCategoriesDto createFromParcel(Parcel source) {
            return new QingMangCategoriesDto(source);
        }

        @Override
        public QingMangCategoriesDto[] newArray(int size) {
            return new QingMangCategoriesDto[size];
        }
    };
}
