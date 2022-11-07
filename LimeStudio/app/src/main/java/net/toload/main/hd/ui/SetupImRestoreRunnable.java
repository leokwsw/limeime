/*
 *
 *  *
 *  **    Copyright 2015, The LimeIME Open Source Project
 *  **
 *  **    Project Url: http://github.com/lime-ime/limeime/
 *  **                 http://android.toload.net/
 *  **
 *  **    This program is free software: you can redistribute it and/or modify
 *  **    it under the terms of the GNU General Public License as published by
 *  **    the Free Software Foundation, either version 3 of the License, or
 *  **    (at your option) any later version.
 *  *
 *  **    This program is distributed in the hope that it will be useful,
 *  **    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  **    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  **    GNU General Public License for more details.
 *  *
 *  **    You should have received a copy of the GNU General Public License
 *  **    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *
 */

package net.toload.main.hd.ui;

import android.os.RemoteException;

import net.toload.main.hd.DBServer;
import net.toload.main.hd.Lime;
import net.toload.main.hd.R;
import net.toload.main.hd.SearchServer;
import net.toload.main.hd.global.LIMEPreferenceManager;

import java.io.FileOutputStream;

/**
 * Created by Art Hung on 2015/4/26.
 */
public class SetupImRestoreRunnable implements Runnable {

    // Global
    private String mType = null;
    private SetupImFragment mFragment = null;
    private LIMEPreferenceManager mLIMEPref;
    private SearchServer SearchSrv = null;

    private SetupImHandler mHandler;
    //private GoogleAccountCredential credential;

    private FileOutputStream mFos;
    private boolean mCanceled;

    public SetupImRestoreRunnable(SetupImFragment fragment, SetupImHandler handler, String type) {
        this.mHandler = handler;
        this.mType = type;
        this.mFragment = fragment;
        this.SearchSrv = new SearchServer(this.mFragment.getActivity());
        this.mLIMEPref = new LIMEPreferenceManager(this.mFragment.getActivity());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    public void run() {

        // Clean the cache before restore the data
        this.SearchSrv.initialCache();

        switch (mType) {
            case Lime.LOCAL:
                try {
                    mHandler.showProgress(true, this.mFragment.getResources().getString(R.string.setup_im_restore_message));
                    DBServer.restoreDatabase();
                    mHandler.cancelProgress();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }

        // Revoke the flag to force application check the payment status
        // mLIMEPref.setParameter(Lime.PAYMENT_FLAG, false);
    }

    /*private void restoreFromGoogle() {

        Drive service = getDriveService(credential);

        List<com.google.api.services.drive.model.File> result = new ArrayList<>();
        try {
            int count = 0;
            boolean continueload = false;
            Drive.Files.List request = service.files().list();
            com.google.api.services.drive.model.File cloudbackupfile = null;

            // Search and get cloudbackupfile
            do {
                FileList files = request.execute();
                for (com.google.api.services.drive.model.File f : files.getItems()) {
                    if (f.getTitle().equalsIgnoreCase(Lime.GOOGLE_BACKUP_FILENAME)) {
                        cloudbackupfile = f;
                        continueload = false;
                        break;
                    }
                }
                count += files.getItems().size();
                if (!continueload || count >= Lime.GOOGLE_RETRIEVE_MAXIMUM) {
                    break;
                }
            } while (request.getPageToken() != null &&
                    request.getPageToken().length() > 0);

            if (cloudbackupfile != null) {
                //java.io.File tempfile = new java.io.File(Lime.DATABASE_FOLDER_EXTERNAL + Lime.DATABASE_CLOUD_TEMP);
                File limedir = new File(LIME.LIME_SDCARD_FOLDER + File.separator);
                if (!limedir.exists()) {
                    limedir.mkdirs();
                }
                File tempfile = new File(LIME.LIME_SDCARD_FOLDER + File.separator + LIME.DATABASE_CLOUD_TEMP);


                if (tempfile.exists()) {
                    tempfile.delete();
                }

                *//*Log.i("LIME", cloudbackupfile.getId());
                Log.i("LIME", cloudbackupfile.getTitle());
                Log.i("LIME", cloudbackupfile.getDescription());
                Log.i("LIME", cloudbackupfile.getDownloadUrl());
*//*
                InputStream fi = downloadFile(service, cloudbackupfile);
                FileOutputStream fo = new FileOutputStream(tempfile);

                int bytesRead;
                byte[] buffer = new byte[8 * 1024];
                if (fi != null) {
                    while ((bytesRead = fi.read(buffer)) != -1) {
                        fo.write(buffer, 0, bytesRead);
                    }
                }

                fo.close();
                //Log.i("LIME", tempfile.getAbsoluteFile() + " -> " + tempfile.length());


                // Decompress tempfile
                //DBServer.decompressFile(tempfile, Lime.DATABASE_DEVICE_FOLDER, Lime.DATABASE_NAME, true);
                DBServer.restoreDatabase(tempfile.getAbsolutePath());

                DBServer.showNotificationMessage(mFragment.getResources().getString(R.string.l3_initial_cloud_restore_end));
            } else {
                DBServer.showNotificationMessage(mFragment.getResources().getString(R.string.l3_initial_cloud_restore_error));
            }

            mLIMEPref.setParameter(Lime.DATABASE_DOWNLOAD_STATUS, "true");

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        mHandler.cancelProgress();

    }

    private Drive getDriveService(GoogleAccountCredential credential) {
        return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential).build();
    }*/

//    private static InputStream downloadFile(Drive service, com.google.api.services.drive.model.File file) {
//        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//            try {
//                HttpResponse resp =
//                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
//                                .execute();
//                return resp.getContent();
//            } catch (IOException e) {
//                // An error occurred.
//                e.printStackTrace();
//                return null;
//            }
//        } else {
//            // The file doesn't have any content stored on Drive.
//            return null;
//        }
//    }
}

