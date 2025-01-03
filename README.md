# SE-lab-design-patterns



# گام سوم

در کل الگوی strategy جایی به کار می‌رود که الگوریتمی داشته باشیم که بخواهیم در زمان رانتایم به صورت دینامیک بتوانیم تغییر دهیم یا انتخاب کنیم.
برای پیاده کردن این الگو، اول interfaceی در نظر می‌گیریم با تابعی که قرار است الگوریتمی را برای تحقق هدفی پیاده کند. حال می‌توانیم به تعداد الگوریتم‌هایی که قصد پیاده کردن آنها را داریم پیاده‌سازی‌های متفاوتی از این interface ارائه دهیم. خروجی این تابع در هر یک از پیاده‌سازی‌هایی که داریم، حاصل همان کاریست که قصد انجام آن را داریم (که نام تابع interface بیانگز آن است) ولی الگوریتم رسیدن به آن برای هر پیاده‌سازی متفاوت است.
برای مثال در کد داده شده می‌توان این الگو را مشاهده کرد.

![image](https://github.com/user-attachments/assets/8bd21b00-594f-4ae5-988d-e18b95208e41)

در این پروژه traverser یک اینترفیس است که یک تابع traverse دارد که قرار است به عنوان خروجی پیمایشی از یک گراف ارائه دهد. این پیمایش می‌تواند طبق الگوریتم‌های متعددی انجام شود که به ازای دو مورد از آنها یعنی BFS و DFS پیاده‌سازی‌هایی از این اینترفیس داریم که هر گدام پیمایش را طبق الگوریتم خود انجام می‌دهد و به عنوان خروجی یک پیمایش از گراف ارائه می‌دهد.

چرا استفاده از این الگو قابل قبول است؟
از آنجایی که پیمایش گراف خروجی واحدی خواهد داشت (مجموعه‌ی تمام رئوس گراف که تنها ترتیب آن می‌تواند فرق کند) و تنها الگوریتم آن تفاوت می‌کند، استفاده از این الگو منطقی است. هر جایی پیمایشی از گراف بخواهیم هر پیاده‌سازی از این interface جوابگوی کار ماست و در عین حال در رانتایم این آزادی را داریم که الگوریتم آن را خودمان انتخاب کنیم.
