package abhiandroid.com.jsonparsingexample;

public class SubDiseaseItem {
        private String subDisease;
        private String imageUrl1;
        private String fileText1;
        private String imageUrl2;
        private String fileText2;




    public SubDiseaseItem (String mSubDisease, String mFileText1, String mImageUrl1, String mFileText2, String mImageUrl2) {
            subDisease = mSubDisease;
            imageUrl1= mImageUrl1;
            fileText1= mFileText1;
            imageUrl2= mImageUrl2;
            fileText2= mFileText2;


        }

    public String getSubDisease() {
        return subDisease;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public String getFileText1() {
        return fileText1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public String getFileText2() {
        return fileText2;
    }
}

