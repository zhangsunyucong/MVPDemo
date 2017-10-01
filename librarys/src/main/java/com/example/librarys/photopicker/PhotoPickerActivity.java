package com.example.librarys.photopicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librarys.R;
import com.example.librarys.photopicker.entity.Photo;
import com.example.librarys.photopicker.event.OnItemCheckListener;
import com.example.librarys.photopicker.fragment.ImagePagerFragment;
import com.example.librarys.photopicker.fragment.PhotoPickerFragment;
import com.example.librarys.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.librarys.photopicker.PhotoPicker.DEFAULT_COLUMN_NUMBER;
import static com.example.librarys.photopicker.PhotoPicker.DEFAULT_MAX_COUNT;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_GRID_COLUMN;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_MAX_COUNT;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_ORIGINAL_PHOTOS;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_PREVIEW_ENABLED;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_SHOW_CAMERA;
import static com.example.librarys.photopicker.PhotoPicker.EXTRA_SHOW_GIF;
import static com.example.librarys.photopicker.PhotoPicker.KEY_SELECTED_PHOTOS;

public class PhotoPickerActivity extends AppCompatActivity {

  private PhotoPickerFragment pickerFragment;
  private ImagePagerFragment imagePagerFragment;

  private ImageView img_left;
  private TextView tv_title;
  private Button btn_right;

  private int maxCount = DEFAULT_MAX_COUNT;

  private int columnNumber = DEFAULT_COLUMN_NUMBER;
  private ArrayList<String> originalPhotos = null;
  private boolean showCamera;
  private boolean showGif;
  private boolean previewEnabled;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getIntentData();

    setShowGif(showGif);

    setContentView(R.layout.__picker_activity_photo_picker);

      setStatusBar();

    initTitleBar();

    addFragment();

  }

    private void setStatusBar() {
        StatusBarUtil.setColor(this,
                this.getResources().getColor(R.color.colorPrimaryDark), 50);
    }

  private void getIntentData() {
      showCamera      = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
      showGif         = getIntent().getBooleanExtra(EXTRA_SHOW_GIF, false);
      previewEnabled  = getIntent().getBooleanExtra(EXTRA_PREVIEW_ENABLED, true);
      maxCount = getIntent().getIntExtra(EXTRA_MAX_COUNT, DEFAULT_MAX_COUNT);
      columnNumber = getIntent().getIntExtra(EXTRA_GRID_COLUMN, DEFAULT_COLUMN_NUMBER);
      originalPhotos = getIntent().getStringArrayListExtra(EXTRA_ORIGINAL_PHOTOS);
  }

  private void addFragment() {
    pickerFragment = (PhotoPickerFragment) getSupportFragmentManager().findFragmentByTag("tag");
    if (pickerFragment == null) {
      pickerFragment = PhotoPickerFragment
              .newInstance(showCamera, showGif, previewEnabled, columnNumber, maxCount, originalPhotos);
      getSupportFragmentManager()
              .beginTransaction()
              .replace(R.id.container, pickerFragment, "tag")
              .commit();
      getSupportFragmentManager().executePendingTransactions();
    }

    pickerFragment.getPhotoGridAdapter().setOnItemCheckListener(new OnItemCheckListener() {
      @Override public boolean onItemCheck(int position, Photo photo, final int selectedItemCount) {

            btn_right.setEnabled(selectedItemCount > 0);
            btn_right.setTextColor((selectedItemCount > 0)
                    ? getResources().getColor(R.color.white)
                    : getResources().getColor(R.color.gray_color));

            if (maxCount <= 1) {
                List<String> photos = pickerFragment.getPhotoGridAdapter().getSelectedPhotos();
                if (!photos.contains(photo.getPath())) {
                  photos.clear();
                  pickerFragment.getPhotoGridAdapter().notifyDataSetChanged();
                }
                if(selectedItemCount <= maxCount) {
                    btn_right.setText(getString(R.string.__picker_done_with_count,
                            selectedItemCount, maxCount));
                }

                return true;
            } else {

                if (selectedItemCount > maxCount) {
                    Toast.makeText(getActivity(), getString(R.string.__picker_over_max_count_tips, maxCount),
                            LENGTH_LONG).show();
                    return false;
                }

                btn_right.setText(getString(R.string.__picker_done_with_count,
                        selectedItemCount, maxCount));
            }

            return true;
      }
    });
  }

  private void initTitleBar() {
      img_left = (ImageView) findViewById(R.id.img_left);
      tv_title = (TextView) findViewById(R.id.tv_title);
      btn_right = (Button) findViewById(R.id.btn_right);
      tv_title.setText(R.string.__picker_title);
      btn_right.setVisibility(View.VISIBLE);
      img_left.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
      });

      btn_right.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Intent intent = new Intent();
            ArrayList<String> selectedPhotos = pickerFragment.getPhotoGridAdapter().getSelectedPhotoPaths();
            intent.putStringArrayListExtra(KEY_SELECTED_PHOTOS, selectedPhotos);
            setResult(RESULT_OK, intent);
            finish();
          }
        });
      if (originalPhotos != null && originalPhotos.size() > 0) {
        btn_right.setTextColor(getResources().getColor(R.color.white));
        btn_right.setEnabled(true);
        btn_right.setText(
                getString(R.string.__picker_done_with_count, originalPhotos.size(), maxCount));
      } else {
        btn_right.setText(
                getString(R.string.__picker_done_with_count, 0, maxCount));
        btn_right.setEnabled(false);
        btn_right.setTextColor(getResources().getColor(R.color.gray_color));
      }
  }

  /**
   * Overriding this method allows us to run our exit animation first, then exiting
   * the activity when it complete.
   */
  @Override public void onBackPressed() {
    if (imagePagerFragment != null && imagePagerFragment.isVisible()) {
      imagePagerFragment.runExitAnimation(new Runnable() {
        public void run() {
          if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
          }
        }
      });
    } else {
      super.onBackPressed();
    }
  }

  public void addImagePagerFragment(ImagePagerFragment imagePagerFragment) {
    this.imagePagerFragment = imagePagerFragment;
    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.container, this.imagePagerFragment)
        .addToBackStack(null)
        .commit();
  }

  public PhotoPickerActivity getActivity() {
    return this;
  }

  public boolean isShowGif() {
    return showGif;
  }

  public void setShowGif(boolean showGif) {
    this.showGif = showGif;
  }
}
