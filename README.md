# SE-lab-design-patterns



# گام اول - پیاده‌سازی الگوی Adapter

## انتخاب نوع Adapter
در اینجا چون می‌خواهم واسط
graph
بسازم نه کلاس گراف، امکان استفاده از هر دو مدل 
object scope و class scope
را در جاوا دارم. (جاوا اجازه ارث‌بری از دو کلاس را نمی‌دهد.)

با این حال دلیل اینکه همچنان از 
object scope
استفاده می‌کنم، اصل
composition over inheritance
است. ارث‌بری از دو موجودیت خوانایی را کم می‌کند و وابستگی بیشتری نسبت به 
object scope
ایجاد می‌کند.
از این رو استفاده از 
object scope
منطقی‌تر است.

به علاوه، تضمینی وجود ندارد که کلاس کتابخانه مورد نظر اجازه 
override
بدهد و ممکن است بعدا کتابخانه‌ای که می‌خواهیم آن را اضافه کنیم این امکان را نداشته و به مشکل بخوریم و مجبور شویم اصل
OCP
را نقض کرده تا مشکل را حل کنیم.

## نحوه پیاده‌سازی الگو
ابتدا یک واسط گراف ساختم:

```java
public interface Graph {
    void addVertex(int v);
    void addEdge(String name, int sourceVertex, int destinationVertex);
    Collection<Integer> getNeighbors(int v);
}
```

سپس پیاده‌سازی از کتابخانه را در آداپتر زیر قرار دادم:

```java
public class JungGraphAdapter implements Graph {
    SparseMultigraph<Integer, String> graph = new SparseMultigraph<>();

    @Override
    public void addVertex(int v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String name, int sourceVertex, int destinationVertex) {
        graph.addEdge(name, sourceVertex, destinationVertex);
    }

    @Override
    public Collection<Integer> getNeighbors(int v) {
        return graph.getNeighbors(v);
    }
}
```

سپس در کل کد، هر جا مستقیم از
SparseMultigraph
استفاده شده بود، از 
Graph
که واسط خودم است، استفاده کردم.

همچنین نتیجه ریفکتور را با قبل آن مقایسه کردم و خروجی‌ها یکسان بود.

# گام دوم - تغییر کتابخانه
## تغییر کتاب خانه 
برای تغییر کتاب خانه فقط در فایل main پروژه، import مربوط به adapter  کتاب خانه jGpathT را جایگزین import مربوط به adapter کتاب خانه jung کردم. همچنین کد زیر را برای ساخت graph به main اضافه کردم :

```java
Graph graph = new JGraphTAdapter();
```
## پیاده سازی adapter جدید
در adapter از کتابخانه مربوط به jGraphT، کلاس SimpleGraph را import کردم و توابع موجود در interface مربوط به graph را طبق توابع موجود در کلاس SimpleGraph پیاده سازی کردم که کد آن در زیر موجود است.
```java
package org.example.graph;

import org.jgrapht.graph.SimpleGraph;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class JGraphTAdapter implements Graph {
    SimpleGraph<Integer, String> graph = new SimpleGraph<>(String.class);

    @Override
    public void addVertex(int v) {
        graph.addVertex(v);
    }

    @Override
    public void addEdge(String name, int sourceVertex, int destinationVertex) {
        graph.addEdge(sourceVertex, destinationVertex, name);
    }

    @Override
    public Collection<Integer> getNeighbors(int v) {
        return graph.edgesOf(v).stream()
                .flatMap(edge -> {
                    Integer source = graph.getEdgeSource(edge);
                    Integer target = graph.getEdgeTarget(edge);
                    return source.equals(v) ? Set.of(target).stream() : Set.of(source).stream();
                })
                .collect(Collectors.toSet());
    }
}

```
در نهایت با انجام این تغییرات و اجرای پروژه، همان خروجی مورد انتظار بدست آمد.

# گام سوم

در کل الگوی strategy جایی به کار می‌رود که الگوریتمی داشته باشیم که بخواهیم در زمان رانتایم به صورت دینامیک بتوانیم تغییر دهیم یا انتخاب کنیم.
برای پیاده کردن این الگو، اول interfaceی در نظر می‌گیریم با تابعی که قرار است الگوریتمی را برای تحقق هدفی پیاده کند. حال می‌توانیم به تعداد الگوریتم‌هایی که قصد پیاده کردن آنها را داریم پیاده‌سازی‌های متفاوتی از این interface ارائه دهیم. خروجی این تابع در هر یک از پیاده‌سازی‌هایی که داریم، حاصل همان کاریست که قصد انجام آن را داریم (که نام تابع interface بیانگز آن است) ولی الگوریتم رسیدن به آن برای هر پیاده‌سازی متفاوت است.
برای مثال در کد داده شده می‌توان این الگو را مشاهده کرد.

![image](https://github.com/user-attachments/assets/8bd21b00-594f-4ae5-988d-e18b95208e41)

در این پروژه traverser یک اینترفیس است که یک تابع traverse دارد که قرار است به عنوان خروجی پیمایشی از یک گراف ارائه دهد. این پیمایش می‌تواند طبق الگوریتم‌های متعددی انجام شود که به ازای دو مورد از آنها یعنی BFS و DFS پیاده‌سازی‌هایی از این اینترفیس داریم که هر گدام پیمایش را طبق الگوریتم خود انجام می‌دهد و به عنوان خروجی یک پیمایش از گراف ارائه می‌دهد.

چرا استفاده از این الگو قابل قبول است؟
از آنجایی که پیمایش گراف خروجی واحدی خواهد داشت (مجموعه‌ی تمام رئوس گراف که تنها ترتیب آن می‌تواند فرق کند) و تنها الگوریتم آن تفاوت می‌کند، استفاده از این الگو منطقی است. هر جایی پیمایشی از گراف بخواهیم هر پیاده‌سازی از این interface جوابگوی کار ماست و در عین حال در رانتایم این آزادی را داریم که الگوریتم آن را خودمان انتخاب کنیم.
