# DalAssist
Group 17 Mobile Computing


# Project Title
DAL Assist is an application which will be useful to the current and future students of Dalhousie University in the faculty of computer science. This app will act as a digital guide for international students also. Our objective is to incorporate all the information and solution to the problems that students experience when they come to the city of Halifax for the first time. Our application will guide the newcomers step by step and help them in settling in the new environment. This application will be used solely by students of Dalhousie University. It will save time and effort of the students and prevent them from getting into any sort of problem just because they are not familiar to the new area.

## Libraries
**google-gson:** https://github.com/google/gson
**appcompat:**https://developer.android.com/topic/libraries/support-library/packages.html
**constraint-layout**https://developer.android.com/topic/libraries/support-library/packages.html
**firebase-database**https://firebase.google.com/docs/android/setup
**firebase-storage:**https://firebase.google.com/docs/android/setup
**firebase-core:**https://firebase.google.com/docs/android/setup
**firebase-auth:**https://firebase.google.com/docs/android/setup
**espresso-core:**https://developer.android.com/topic/libraries/support-library/packages.html
**cardview**https://developer.android.com/topic/libraries/support-library/packages.html
**design:**https://developer.android.com/topic/libraries/support-library/packages.html
**support:**https://developer.android.com/topic/libraries/support-library/packages.html
**recyclerview:**https://developer.android.com/topic/libraries/support-library/packages.html
**gms:play-services-auth:**https://developers.google.com/android/guides/releases
**multidex:**https://developer.android.com/topic/libraries/support-library/packages.html
**ssp-android:**https://github.com/intuit/ssp
**ssd-android:**https://github.com/intuit/sdp
**picasso:picasso:** https://github.com/square/picasso
**gms.google-services**https://developers.google.com/android/guides/google-services-plugin
***

## Installation Notes

1. You will need an android device with at least Marshmallow 6.0 update OS running.
2. Download the zip file or the repository from the "https://github.com/hemanthkurra/DalAssist.git"
3. Run it with android studio.
Note: If you are a new user and your SHA 1 key is updated to the firebase then you won't be able to login in Assist with google sign up.

## Code Examples

**Problem 1: On request Call action permission**

In Baking activity (inside activity of catalog) we have added the information of different banks with their website and customer service number. We wanted to add feature for user to directly call the bank from the app. As we were familiar with the auto link but we wanted user's permission. Hence, we came up with this solution:
```
// The method we implemented that solved our problem
 public void call_action2() {
        final TextView t = findViewById(R.id.textview6);
        t.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +t));
                if (ActivityCompat.checkSelfPermission(Banking.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });

    }


// Source: Stackoverflow.com [30]
```

**Problem 2: BottomBar**

After the project update 1 we realized that drawer layout is more confusing for user. We needed a new layout for user to navigate within application activities. Hence, we came up with the Bottom navigation menu bar solution:
```
// The method we implemented that solved our problem
BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener( ) {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId( )) {
                            case R.id.nav_assist:
                                Intent first = new Intent(getApplicationContext(), googlesignin.class);
                                startActivity(first);
                                break;
                            case R.id.nav_catalog:
                                Intent second = new Intent(getApplicationContext( ), Catalog.class);
                                startActivity(second);
                                break;
                            case R.id.nav_myaccount:
                                Intent third = new Intent(getApplicationContext(), LoginFrontActivity.class);
                                startActivity(third);
                                break;
                        }
                        return true;
                    }
                });
    }



// Source:  Stackoverflow.com [18]
```
**Problem 3: Google sign in**
We have implemented google sign in using firebase and it is working fine. But we realized there is some problem (API exception) with login when we are trying to import the project from GitHub to different systems. We could get the exact issue and after referring several cites such as stack over flow and firebase documentation we found that the issue is with SHA key and the intermediate solution is to add the SHA1 key finger print in to the firebase console.  
```
@Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == RC_SIGN_IN) {
           @SuppressLint("RestrictedApi") Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           try {
               // Google Sign In was successful, authenticate with Firebase
               GoogleSignInAccount account = task.getResult(ApiException.class);
               firebaseAuthWithGoogle(account);

           } catch (ApiException e) {


               Toast.makeText(this, "Google Sign In Failed", Toast.LENGTH_SHORT).show();
           }
       }
   }

```
**Problem 4: Retrieving image from the firebase storage**
We faced a issue in assist functionality when retrieving the image from the firebase storage after uploading to the storage. we found that the problem was the code is trying to fetch the image but it is not available in the storage and we encountered null exception. this is because firebase has some wait time to upload the image and when code is trying to fetch the image is not available. The problem can be solved by using wait time in the retrieving code.

```
if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

               selectedImage = Uri.fromFile(mImageFile);

               Log.e(TAG, "onActivityResult: selectedImage camera" + selectedImage);
               String file = compressImage(String.valueOf(data.getData()));
               thumbnailpic = Uri.fromFile(new File(file));

               mediumpic = Uri.fromFile(new File(file));


               Log.d("ImagePath", "Image saved to path : " + mImageFile.getAbsolutePath());

               Toast.makeText(uploadimage.this, "Path: " + mImageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
               uploadImage.setImageURI(tempURI);
               tempURI = null;

           }
       }catch (Exception e){
           Log.e(TAG, "onActivityResult: "+e.getMessage() );
       }

```

## Feature Section
There are some main features in the application which are listed below:
- Catalog is a list of different categories, where new or frequent users can browse through the list of categories like Student Guide, Banking, Health Care etc.
- Assist that provides a user friendly interface to communicate with locals, to write down their queries and find the appropriate assistance from the responders.
- Different Logins for Staff and students.
- Events will be reflected once registered for them.
- Staff will be listing availability time and student can see it and book an appointment. It will be reflected to student as soon as teacher uploads availability time.
- The primary feature is that the user do not have to  search different forums for every single information. This allows the users to access the entire information in one app.
- Students can login to Bright Space and upload and download assignments.

## Final Project Status
The basic and expected functionalities were of key importance to make the app functional. The future students use this app for fetching every bit of
information about the city Halifax, their accommodation, the University, their courses, the staff and faculty and all that needed to make their stay easier once they arrive here to Halifax. The existing students can utilize My Account feature of the app to access all their course details, organize their schedule, know about the events and workshops happening in the university and etc. The students simply have to register and start using the app. Team has tried to have achieved all the goals for this project and have been completed to a great extent. If the project was to be further completed, next steps would have included more Departments information from student to faculty of the University to be provided. Making it accessible and useful for other departments.

#### Minimum Functionality
- Catalog (Completed)
- Assist (Completed)
- My Account (Completed)

#### Expected Functionality
- Events (Completed)
- Staff and Faculty (Completed)
- Academic Calendar (Completed)
- Course information (Completed)

#### Bonus Functionality
- My Digest(Partially Completed)

## Sources

[1] "Wiki," [Online]. Available: https://en.wikipedia.org/wiki/Canada_Deposit_Insurance_Corporation.
[2] "ScotiaBank," [Online]. Available: http://www.scotiabank.com/ca/en/0,,2,00.html.
[3] "TDBank," [Online]. Available: https://www.td.com/ca/en/personal-banking/.
[4] "CIBCBank," [Online]. Available: https://www.cibc.com/en/personal-banking.html.
[5] "RBCBank," [Online]. Available: https://www.rbcroyalbank.com/personal.html.
[6] "BMOBank," [Online]. Available: https://www.bmo.com/main/personal.
[7] "Immigration," [Online]. Available: https://www.canada.ca/en/services/immigration-citizenship.html.
[8] "halifaxAirport," [Online]. Available: https://halifaxstanfield.ca/.
[9] "taxiService," [Online]. Available: https://driverdaves.com/.
[10] "transit," [Online]. Available: https://www.halifax.ca/transportation/halifax-transit.
[11] "DSU," [Online]. Available: https://www.internationalhealth.ca/dsu/155-what-covered.
[12] "Ultimate Guide to Bottom Navigation on Android – AndroidPub", AndroidPub, 2018. [Online]. Available: https://android.jlelse.eu/ultimate-guide-to-bottom-navigation-on-android-75e4efb8105f. [Accessed: 08- Apr- 2018].
[13] "Implementing Bottom Navigation View in your app", Segunfamisa.com, 2018. [Online]. Available: https://segunfamisa.com/posts/bottom-navigation-view-android. [Accessed: 08- Apr- 2018].
[14] "BottomNavigationView | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/reference/android/support/design/widget/BottomNavigationView.html. [Accessed: 08- Apr- 2018].
[15] "Bottom navigation - Components - Material Design", Material Design, 2018. [Online]. Available: https://material.io/guidelines/components/bottom-navigation.html. [Accessed: 08- Apr- 2018].
[16] "BottomNavigationView - How to avoid recreation of Fragments and reuse them", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/45130713/bottomnavigationview-how-to-avoid-recreation-of-fragments-and-reuse-them. [Accessed: 08- Apr- 2018].
[17] "BottomNavigationView between activities", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/41744219/bottomnavigationview-between-activities. [Accessed: 08- Apr- 2018].
[18] "Android new Bottom Navigation bar", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/36032177/android-new-bottom-navigation-bar. [Accessed: 08- Apr- 2018].
[19] "Android remember me in log in activity", Worldbestlearningcenter.com, 2018. [Online]. Available: http://www.worldbestlearningcenter.com/tips/Android-remember-me-in-log-in-activity.htm. [Accessed: 08- Apr- 2018].
[20] "Data and File Storage Overview | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/guide/topics/data/data-storage.html#pref. [Accessed: 08- Apr- 2018].
[21] "Apps - how to create a session for remember me (login authentication) in android", Android Forums, 2018. [Online]. Available: https://androidforums.com/threads/how-to-create-a-session-for-remember-me-login-authentication-in-android.708810/. [Accessed: 08- Apr- 2018].
[22] "Android login with database using shared preferences PART 1", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=LjBD7iGRvS4. [Accessed: 08- Apr- 2018].
[23] "Save Key-Value Data with SharedPreferences | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/training/data-storage/shared-preferences.html. [Accessed: 08- Apr- 2018].
[24] "Android Studio Tutorial - Grid Layout Item Click", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=K2V6Y7zQ8NU. [Accessed: 08- Apr- 2018].
[25] "Android Studio Tutorial - Grid Layout and CardView", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=VUPM387qyrw. [Accessed: 08- Apr- 2018].
[26] "Android Studio Tutorial - Grid Layout Scrollable and Click to new Activity edmt dev", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=go9q4O44b4E. [Accessed: 08- Apr- 2018].
[27] "Material Home Page Design (Dashboard) in android studio", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=-YCUrHFUxlA. [Accessed: 08- Apr- 2018].
[28] Gridview having clickable images, "Creating Gridview having clickable images,Android", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/11947452/creating-gridview-having-clickable-images-android. [Accessed: 08- Apr- 2018].
[29] "Grid View | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/guide/topics/ui/layout/gridview.html. [Accessed: 08- Apr- 2018].
[30] "android request runtime permission to call action", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/42057040/android-request-runtime-permission-to-call-action. [Accessed: 08- Apr- 2018].
[31] "Android marshmallow request permission?", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/questions/33666071/android-marshmallow-request-permission. [Accessed: 08- Apr- 2018].
[32] "Request runtime permissions appropriately | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/distribute/best-practices/develop/runtime-permissions.html. [Accessed: 08- Apr- 2018].
[33] "Request App Permissions | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/training/permissions/requesting.html. [Accessed: 08- Apr- 2018].
[34] "Holiday icons collection", Freepik, 2018. [Online]. Available: https://www.freepik.com/free-vector/holiday-icons-collection_1089394.htm#term=icon&page=2&position=22. [Accessed: 08- Apr- 2018].
[35] "Image: BMO Bank of Montreal - Township of Assiginack", Google.ca, 2018. [Online]. Available: https://www.google.ca/imgres?imgurl=http%3A%2F%2Fwww.assiginack.ca%2Fwp-content%2Fuploads%2F2016%2F12%2FBMO.jpg&imgrefurl=http%3A%2F%2Fwww.assiginack.ca%2Fbusinesses%2Fbmo-bank-montreal%2F&docid=Yezf_EBhU2isGM&tbnid=cL9aJXR7ELJPUM%3A&vet=10ahUKEwjKxITLxavaAhXRdN8KHRaRAYIQMwgyKAAwAA..i&w=600&h=287&bih=587&biw=1229&q=BMO%20BANK&ved=0ahUKEwjKxITLxavaAhXRdN8KHRaRAYIQMwgyKAAwAA&iact=mrc&uact=8. [Accessed: 08- Apr- 2018].
  [36] "Image: TD Bank Halts Digital Currency Purchases made with Credit Cards", Google.ca, 2018. [Online]. Available: https://www.google.ca/imgres?imgurl=https%3A%2F%2Fdiscover.coinsquare.io%2Fwp-content%2Fuploads%2F2018%2F02%2Ftd-bank.png&imgrefurl=https%3A%2F%2Fdiscover.coinsquare.io%2Fdigital-currency%2Ftd-bank-halts-digital-currency-purchases-made-credit-cards%2F&docid=OXl3pcil4r9lxM&tbnid=V9cZvz5u0G-haM%3A&vet=10ahUKEwiqia_sxavaAhWrd98KHcnzBYkQMwiuASgAMAA..i&w=1200&h=1074&bih=572&biw=614&q=td%20bank&ved=0ahUKEwiqia_sxavaAhWrd98KHcnzBYkQMwiuASgAMAA&iact=mrc&uact=8. [Accessed: 08- Apr- 2018].

[37] "Image: Cross-Border Banking for Canadians in the US—RBC Bank", Google.ca, 2018. [Online]. Available: https://www.google.ca/imgres?imgurl=https%3A%2F%2Fwww.rbcbank.com%2Fcross-border%2F_assets-custom%2Fimages%2Flogos%2Frbc-shield.svg&imgrefurl=https%3A%2F%2Fwww.rbcbank.com%2Findex.html&docid=_HrMVs5MeFCHMM&tbnid=NN2AG-B-qHH9yM%3A&vet=10ahUKEwj9jvOBxqvaAhWxneAKHR9JDn8QMwhmKAAwAA..i&w=627&h=800&bih=572&biw=614&q=rbc%20bank&ved=0ahUKEwj9jvOBxqvaAhWxneAKHR9JDn8QMwhmKAAwAA&iact=mrc&uact=8. [Accessed: 08- Apr- 2018].
[38] "Image: Scotiabank on the Forbes Canada's Best Employers List", Google.ca, 2018. [Online]. Available: https://www.google.ca/imgres?imgurl=https%3A%2F%2Fi.forbesimg.com%2Fmedia%2Flists%2Fcompanies%2Fscotiabank_416x416.jpg&imgrefurl=https%3A%2F%2Fwww.forbes.com%2Fcompanies%2Fscotiabank%2F&docid=ddcsJwQnoIX8UM&tbnid=J0QPmGIIDABH1M%3A&vet=10ahUKEwjb9quJxqvaAhVRON8KHV2VDHoQMwhUKAMwAw..i&w=416&h=416&bih=572&biw=614&q=SCOTIA%20BANk&ved=0ahUKEwjb9quJxqvaAhVRON8KHV2VDHoQMwhUKAMwAw&iact=mrc&uact=8. [Accessed: 08- Apr- 2018].
[39] "Image: Personal Banking | Financial Services | CIBC", Google.ca, 2018. [Online]. Available: https://www.google.ca/imgres?imgurl=https%3A%2F%2Fwww.cibc.com%2Fcontent%2Fdam%2Fglobal%2Flogo-cibc-keyline.png%2F_jcr_content%2Frenditions%2Fcq5dam.web.1280.1280.png&imgrefurl=https%3A%2F%2Fwww.cibc.com%2Fen%2Fpersonal-banking.html&docid=sbo-M5aht9av_M&tbnid=ObmL0sUL-weNZM%3A&vet=10ahUKEwjn3dmQxqvaAhVsU98KHVmKB24QMwhPKAEwAQ..i&w=163&h=146&bih=572&biw=614&q=CIBC%20BANK&ved=0ahUKEwjn3dmQxqvaAhVsU98KHVmKB24QMwhPKAEwAQ&iact=mrc&uact=8. [Accessed: 08- Apr- 2018].
[40]	Firebase: Reading Data and Event Listeners. [Online]. Available: https://www.learnhowtoprogram.com/android/data-persistence/firebase-reading-data-and-event-listeners. [Accessed: 08-Apr-2018].
[41]	“Android : How to convert the compressed images to string with minimum size,” bitmap - Android : How to convert the compressed images to string with minimum size - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/43487288/android-how-to-convert-the-compressed-images-to-string-with-minimum-size. [Accessed: 08-Apr-2018].
[42]	“Android RecyclerView within a Fragment, loading data from a content provider not rendering,” Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/49578822/android-recyclerview-within-a-fragment-loading-data-from-a-content-provider-not. [Accessed: 08-Apr-2018].
[43]	“Android: Login with Google Account,” AndroidDeft.com, 28-Jan-2018. [Online]. Available: http://www.androiddeft.com/2018/01/28/android-login-with-google-account. [Accessed: 08-Apr-2018].
[44]	“Change image size when uploaded to server from android device,” Change image size when uploaded to server from android device – Android Forum - AndroidPIT. [Online]. Available: https://www.androidpit.com/forum/627162/change-image-size-when-uploaded-to-server-from-android-device. [Accessed: 08-Apr-2018].
[45]	“Compress image like What's app and Facebook using Android,” Android - Compress image like What's app and Facebook using Android. [Online]. Available: https://www.howtobuildsoftware.com/index.php/how-do/vzQ/android-image-compression-compress-image-like-whats-app-and-facebook-using-android. [Accessed: 08-Apr-2018].
[46]	“firebase/quickstart-android,” GitHub. [Online]. Available: https://github.com/firebase/quickstart-android/blob/master/database/app/src/main/java/com/google/firebase/quickstart/database/PostDetailActivity.java. [Accessed: 08-Apr-2018].
[47]	“How can I get data out of onDataChange method?,” android - How can I get data out of onDataChange method? - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/46396846/how-can-i-get-data-out-of-ondatachange-method. [Accessed: 08-Apr-2018].
[48]	“How can I implement OnClickListener of an ImageView in a fragment?,” android - How can I implement OnClickListener of an ImageView in a fragment? - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/37380798/how-can-i-implement-onclicklistener-of-an-imageview-in-a-fragment. [Accessed: 08-Apr-2018].
[49]	“How to add page title and icon in android FragmentPagerAdapter,” Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/31260384/how-to-add-page-title-and-icon-in-android-fragmentpageradapter. [Accessed: 08-Apr-2018].
[50]	“How to call Activity from a menu item in Android?,” Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/4169714/how-to-call-activity-from-a-menu-item-in-android. [Accessed: 08-Apr-2018].
[51]	“How to use recycler views,” Android Authority, 13-Feb-2018. [Online]. Available: https://www.androidauthority.com/how-to-use-recycler-views-836053/. [Accessed: 08-Apr-2018].
[52]	“Java Code Examples for android.widget.AdapterView.OnItemClickListener,” Java Code Examples android.widget.AdapterView.OnItemClickListener. [Online]. Available: https://www.programcreek.com/java-api-examples/android.widget.AdapterView.OnItemClickListener. [Accessed: 08-Apr-2018].
[53]	B. Khan, B. KhanI, A. Arya, Kerojin, and Patel Milan, “Google Login Android using Firebase Tutorial,” Simplified Coding, 14-Nov-2017. [Online]. Available: https://www.simplifiedcoding.net/google-login-android. [Accessed: 08-Apr-2018].
[54]	“Saving data from a textview to a firebase database,” android - Saving data from a textview to a firebase database - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/48731166/saving-data-from-a-textview-to-a-firebase-database. [Accessed: 08-Apr-2018].
[55]	“Show Firebase Database Data into RecyclerView ListView Tutorial,” Android Examples, 13-Aug-2017. [Online]. Available: https://www.android-examples.com/show-firebase-database-data-into-recyclerview/. [Accessed: 08-Apr-2018].
[56]	“Tab layout with Fragments,” java - Tab layout with Fragments - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/43359837/tab-layout-with-fragments. [Accessed: 08-Apr-2018].
[57]	“Unable to display recyclerview contents in fragment class,” android - Unable to display recyclerview contents in fragment class - Stack Overflow. [Online]. Available: https://stackoverflow.com/questions/44595266/unable-to-display-recyclerview-contents-in-fragment-class. [Accessed: 08-Apr-2018].
[58]	Learn How to Program. [Online]. Available: https://www.learnhowtoprogram.com/intro-to-programming/getting-started-at-epicodus-64627971-b5be-49e7-a8d7-7ca55d0c9e88/learn-how-to-program. [Accessed: 08-Apr-2018].
[59]	“Add Firebase to Your Android Project  |  Firebase,” Google. [Online]. Available: https://firebase.google.com/docs/android/setup. [Accessed: 08-Apr-2018].
[60]	“Developer Guides,” Android Developers, 21-Feb-2018. [Online]. Available: https://developer.android.com/guide/index.html. [Accessed: 08-Apr-2018].
[61]	“google/gson,” GitHub. [Online]. Available: https://github.com/google/gson. [Accessed: 08-Apr-2018].
[62]	“intuit/sdp,” GitHub. [Online]. Available: https://github.com/intuit/sdp. [Accessed: 08-Apr-2018].
[63]	“intuit/ssp,” GitHub. [Online]. Available: https://github.com/intuit/ssp. [Accessed: 08-Apr-2018].
[64]	“Release Notes  |  Google APIs for Android  |  Google Developers,” Google. [Online]. Available: https://developers.google.com/android/guides/releases. [Accessed: 08-Apr-2018].
[65]	“square/picasso,” GitHub. [Online]. Available: https://github.com/square/picasso. [Accessed: 08-Apr-2018].
[66]	“Stack Overflow - Where Developers Learn, Share, & Build Careers,” Stack Overflow - Where Developers Learn, Share, & Build Careers. [Online]. Available: https://stackoverflow.com/. [Accessed: 08-Apr-2018].
[67]	“Support Library Packages,” Android Developers, 05-Apr-2018. [Online]. Available: https://developer.android.com/topic/libraries/support-library/packages.html. [Accessed: 08-Apr-2018].
[68]	“The Google Services Gradle Plugin  |  Google APIs for Android  |  Google Developers,” Google. [Online]. Available: https://developers.google.com/android/guides/google-services-plugin. [Accessed: 08-Apr-2018].
[69]	Google Search. [Online]. Available: https://www.google.ca/search?safe=active&rlz=1C1CHBF_enCA775CA775&tbm=isch&q=upload symbols&chips=q:upload symbols,online_chips:arrow&sa=X&ved=0ahUKEwjp0a3Y5KvaAhXIwFkKHdboCqMQ4lYIKCgB&biw=1280&bih=653&dpr=1.5#imgrc=YGglBfLoNqOAJM: [Accessed: 08-Apr-2018].
[70]	[40] Cic.gc.ca, 2018. [Online]. Available: http://www.cic.gc.ca/english/pdf/pub/study.pdf. [Accessed: 08-Apr- 2018].
