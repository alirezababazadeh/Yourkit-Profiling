# آزمایش پنجم:   آشنایی با نحوه پروفایل برنامه (Profiling)

## مراحل نصب:

ابتدا پروژه را clone کرده و در گیت بارگزاری کردیم که می‌توان با کلیک بر روی این لینک، به مخزن برنامه دسترسی داشت.
مطابق راهنمای داده شده، ابتدا برنامه را نصب کردیم. به جای کرک برنامه، از آنجا که مدت زمان باقی‌مانده تا انتهای ترم کمتر از 2 هفته است و برای این آزمایش نیز تنها 1 هفته وقت داشتیم، در نتیجه لایسنس رایگان دو هفته‌ای، برای تیم ما کافی بود و در نتیجه از همان استفاده کردیم.

![Install Yourkit](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/00c9c37b-eb4e-48a7-97ff-560128fd0d45)


همانطور که مشاهده می‌شود، دارای لایسنس 15 روزه است.
سپس پلاگین IntellijIdea را مطابق دستور کار نصب می‌کنیم و بعد از باز کردن برنامه intellijIdea، از نصب آن مطمئن می‌شویم. همچنین با وجود قابلیت run توسط Profiler، اطمینان حاصل می‌کنیم که مراحل به درستی طی شده‌اند. تصاویر زیر بیانگر این مطالب هستند:

![image](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/46611d05-91b1-4bf2-a83c-ff9a9be7f5a1)

![image](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/43559812-4817-48cd-968a-844ea5065fd3)



## سناریو اول:
### اجرای اولیه JavaCup
با توجه به سوال 1، ابتدا فایل JavaCup را اجرا می‌کنیم. وجود متود main به معنی قابل اجرا بودن این فایل است. سپس گزینه
Profile 'JavaCup.main()'
می‌زنیم. به کمک این گزینه، YourKit می‌تواند Profiling این برنامه را در اختیار بگیرد. سپس به برنامه YourKit می‌رویم و نحوه اجرای برنامه را مشاهده می‌کنیم. چون برنامه در ابتدا ورودی می‌خواهد، پس مشاهده می‌شود که مصرف منابع سیستم، حداقلی است و اتفاقی نمی‌افتد:

![01-waiting](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/639826f5-eba2-4a28-aac3-0da8d5e89922)

برآمدگی‌های موجود در نمودار، به دلیل باجرای برنامه توسط محیط intellij است که مقادیر آنها اندک است و بیانگر مشکلی نیست. بعد از دادن مقادیر لازم، تصویر نمودار به صورت زیر درمی‌آید:

![01-running](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/ef4f7df0-46a8-42b2-b089-09227ba38d77)

همانطور که انتظار می‌رفت، مصرف منابع سیستم تغییر کرده و زیاد شده است. در شکل زیر، تابعی که بیشترین مصرف را دارد، قابل مشاهده است و همانطور که از سورس کد معلوم است، تابع ()temp بیشترین مصرف را دارد:

![01-most resource usage](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/c4ed026c-a49d-4f1c-93a6-a6bbd6ef6d33)

در شکل زیر، مصرف cpu و تابع مربوطه با هم قرار گرفته‌اند:

![01-most resource usage with cpu](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/5b2867c4-b6fd-446c-95c9-503ccd78bb5c)

### اجرای فایل JavaCup پس از تغییر

سپس عملکرد تابع temp را به گونه‌ای تغییر می‌دهیم که میزان مصرف منابع را به حداقل برسانیم. ابتدا الگوریتم تابع را بررسی می‌کنیم. همانطور که مشاهده می‌شود، این تابع هرکدام از اعداد 0 تا 9999 را با اعداد 0 تا 19999 جمع می‌کند. پس از کمی تحلیل، مشاهده می‌شود که اعداد حاصل به صورت زیر هستند:
- هرکدام از اعداد 0 تا 9998 و 20000 تا 29998، به طور یک به یک (0 با 29998، 1 با 29997 و …) تعداد برابری در لیست دارند، و تعداد اعداد 0 تا 9998، برابر یکی بیشتر از مقدار آنهاست (تعداد 0 یکی، تعداد 1 دوتا و …)
- هر کدام از اعداد 9999 تا 19999 نیز، 10هزاربار تکرار شده‌اند.
در نتیجه، یک لول از بهینه سازی این است که به جای sum، هر عدد را به اندازه لازم تکرار کنیم. در گام بعد باید این اعداد را در یک لیست قرار دهیم. اگر از ArrayList استفاده می‌کردیم، هزینه مرج کردن این اعداد بسیار زیاد می‌گشت، به گونه‌ای که از هزینه الگوریتم اولیه نیز بیشتر می‌شد. در این قسمت، به جای استفاده از ArrayList، از LinkedList استفاده می‌کنیم. در این صورت تنها با وصل کردن تعدادی پوینتر به هم، لیست جدید را می‌سازیم و هزینه مصرف منابع با این روش به طور چشم‌گیری کاهش می‌یابد.

در زیر کد تابع جدید آمده است:
```
public static void temp() {
   List<Integer> list = new LinkedList<>();
   for (int  i = 0; i < 9999; i++) {
       list.addAll(Collections.nCopies(i + 1, i));
       list.addAll(Collections.nCopies(i + 1, 29999 - i));
   }
   for (int i = 9999; i < 20000; i++) {
       list.addAll(Collections.nCopies(10000, i));
   }
}
```

در ادامه تصاویر حاصل از Profiling این مجموعه نیز آمده است.
تصویر اول متعلق به زمان انتظار برای دریافت ورودی است، که تغییری با سری قبل ندارد. اما تصاویر بعدی که مربوط به میزان مصرف منابع است، تغییرات را به خوبی نمایش می‌دهند:

![02-waiting](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/3327dcae-288b-43ff-8803-c7abfe1e4e66)

![02-running](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/2a21ffc0-da33-462f-8692-b7349538a02c)

تصویر زیر مربوط به میزان مصرف منابع توسط تابع temp را نشان می‌دهد:

![02-most resource usage](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/30870118-b01a-4e4d-a91e-a50e77502458)

تصویر زیر، بیانگر میزان مصرف تابع temp، به همراه نمایش میزان مصرف cpu به صورت یکجا است:

![02-most resource usage with cpu](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/36379dfd-03a5-4d1c-9149-698983d5ce0c)

همانطور که مشاهده می‌شود، دیگر علامت fire وجود ندارد و میزان مصرف بسیار پایین آمده است که نتیجه مطلوب ما نیز همین بوده است.

## سناریو دوم

در این سناریو میخواهیم 2 به توان عدد های مختلف از دو روش بهینه و غیر بهینه بررسی کنیم.

### روش غیر بهینه

برای مثال در اینجا 2 به توان 1000 را بررسی می‌کنیم . در این روش با استفاده از حلقه هربار بار ضرب در عدد قبلی آن را حساب می کنیم.

![image](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/12fe0238-6d1d-4159-b1a6-11f542deb8f2)

![image13](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/077e472f-7140-4c15-b38b-813a0570c481)

![image2](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/35509910-9eeb-4938-8098-75241a583194)

### روش بهینه

دقیقا مراحل بالا را طی کردیم اما این بار روش محاسبه بهینه بود که می ‌توانید بینید.

![image](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/a3249965-cfb5-4050-b80d-b8cd9fbf4663)

حال با استفاده از برنامه your kit منابع استفاده شده را بررسی می کنیم.

![image4](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/a6ce2ba0-de5e-48f1-bcb6-22739b8bfeaa)

![image10](https://github.com/alirezababazadeh/Yourkit-Profiling/assets/45295180/788b9657-99c3-4b2e-b7a6-8db45212b99a)

