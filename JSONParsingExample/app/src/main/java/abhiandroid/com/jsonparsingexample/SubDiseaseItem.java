package abhiandroid.com.jsonparsingexample;

public class SubDiseaseItem {
        private String mSubDisease;
        private String mImageUrl;


        public SubDiseaseItem (String subDisease, String imageUrl) {
            mSubDisease = subDisease;
            mImageUrl = imageUrl;
        }
        public String getImageUrl() {
            return mImageUrl;
        }
        public String getSubDisease() {
            return mSubDisease;
        }
    }

