##Credit : Lindelwe Michael Ncube (micnubinub : https://github.com/Micnubinub/)

##Credit : Robin Chutaux (traex : https://github.com/traex)

##How to use:
###Note that the recommended layout params are in the main activity

###Material Switch:

####In XML:
```xml
<com.micnubinub.materiallibrary.MaterialSwitch
    android:id="@+id/material_switch"
    android:layout_width="fill_parent"
    android:layout_height="60dp"
    app:text="Material Switch" />
```
####in Java:
```java
MaterialSwitch materialSwitch = (MaterialSwitch) findViewById(R.id.material_switch);
materialSwitch.setOnCheckedChangeListener(new MaterialSwitch.OnCheckedChangedListener() {
    @Override
    public void onCheckedChange(MaterialSwitch materialSwitch, boolean isChecked) {
        //Do your stuff here
    }
});
```

###Material RadioGroup:

####in XML:
```xml
<com.micnubinub.materiallibrary.MaterialRadioGroup
    android:id="@+id/material_radio_group"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content">

    <com.micnubinub.materiallibrary.MaterialRadioButton
        android:layout_width="fill_parent"
        android:layout_height="55dp"
        app:text="Radio Button1" />

    <com.micnubinub.materiallibrary.MaterialRadioButton
        android:layout_width="fill_parent"
        android:layout_height="55dp"
        app:text="Radio Button2" />

    <com.micnubinub.materiallibrary.MaterialRadioButton
        android:layout_width="fill_parent"
        android:layout_height="55dp"
        app:text="Radio Button2" />

</com.micnubinub.materiallibrary.MaterialRadioGroup>
```

####in Java:
```java
MaterialRadioGroup materialRadioGroup = (MaterialRadioGroup) findViewById(R.id.material_radio_group);
materialRadioGroup.setOnSelectionChanged(new MaterialRadioGroup.OnSelectionChangedListener() {
    @Override
    public void onSelectionChanged(MaterialRadioButton radioButton, int selectedChild) {
        //Do your stuff here
    }
});
```

###Material Single Line Text View with Icon/Avatar:

####In XML:
```xml
<com.micnubinub.materiallibrary.MaterialSingleLineTextAvatar
    android:id="@+id/material_single_line_avatar"
    android:layout_width="fill_parent"
    android:layout_height="60dp" />
```
####in Java:
```java
MaterialSingleLineTextAvatar materialSingleLineTextAvatar = (MaterialSingleLineTextAvatar) findViewById(R.id.material_single_line_avatar);
materialSingleLineTextAvatar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPixels(56)));
materialSingleLineTextAvatar.setText("Material Single line text with avatar");
materialSingleLineTextAvatar.setIcon(getResources().getDrawable(R.drawable.icon));

```

###Material Two/Three Line Text View with Icon/Avatar/AvatarWithIcon:

####In XML:
```xml
<com.micnubinub.materiallibrary.MaterialSingleLineTextAvatar
    android:id="@+id/material_avatar_icon"
    android:layout_width="fill_parent"
    android:layout_height="60dp" />

```
####in Java:
```java
MaterialThreeLineTextAvatarWithIcon materialThreeLineTextAvatarWithIcon = (MaterialThreeLineTextAvatarWithIcon) findViewById(R.id.material_avatar_icon);
materialThreeLineTextAvatarWithIcon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPixels(88)));
materialThreeLineTextAvatarWithIcon.setPrimaryText("Primary text");
materialThreeLineTextAvatarWithIcon.setSecondaryText("Material Three line text with Avatar and Icon test, making sure it spans over two lines so i can show case the 3 lines in the three line text");
materialThreeLineTextAvatarWithIcon.setLeftIcon(getResources().getDrawable(R.drawable.avatar));
materialThreeLineTextAvatarWithIcon.setRightIcon(getResources().getDrawable(R.drawable.icon));

```
