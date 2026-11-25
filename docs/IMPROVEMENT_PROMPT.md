# برومبت التطوير الشامل: PRINSS FUT (الموقع + التطبيق)

> انسخ كل ما تحت هذا السطر وأرسله كما هو لمطوّر أو لأداة AI (Claude أو Cursor أو AI Studio).

---

أنت مطوّر Full-Stack خبير في WordPress وWooCommerce وأداء الويب وAndroid (Kotlin/Jetpack Compose). مهمتك تطوير متجر **PRINSS FUT** ليصبح جاهزًا لحملات إعلانية مدفوعة على Meta وTikTok وSnap وGoogle، دون أن تُكسر أي وظيفة قائمة.

## 1. السياق

- **النشاط:** متجر رقمي عربي لبيع وشراء كوينز EA FC 27، وخدمات FUT Champions وSBC، وبطاقات PlayStation وXbox، وألعاب رقمية. يستهدف الخليج ومصر: KW وSA وAE وQA وBH وOM وEG.
- **الموقع:** https://prinssfut.com
  - البنية: WordPress 7.1.5 + WooCommerce 11.3.1 + Elementor 4.4.1 + قالب Woodmart 8.5.7.2 + LiteSpeed Cache 7.9.3 + Rank Math، خلف Cloudflare.
  - الإضافات: WC Multi Currency وWC Custom Product Addons Pro وWPConsent، وويدجت حاسبة كوينز مخصص `.pcpe-widget`.
- **التطبيق:** Android بلغة Kotlin وJetpack Compose، في المستودع `elprinss1990/APP-PRINSS-SAITE`.
  - طُبّقت إصلاحات أولية في PR #1: رقم واتساب موحّد، وحذف البيانات الوهمية، وزر الطلب يرسل عبر واتساب، وتفعيل R8.
- **رقم واتساب الرسمي:** `+96566838043`
- **البريد:** info@prinssfut.com

## 2. نتائج الفحص الحالية (نقطة البداية)

| المؤشر | الحالي | الهدف |
|---|---|---|
| Lighthouse Performance (جوال) | 71 | ≥ 90 |
| LCP | 2.49s | < 2.0s |
| TBT | 1,152ms | < 200ms |
| TTI | 7.96s | < 3.5s |
| CLS | 0.006 | < 0.1 (حافظ عليه) |
| حجم HTML للرئيسية | 581KB (137KB مضغوط) | < 150KB غير مضغوط |
| وزن الصفحة | 1.7MB / 73 طلبًا | < 900KB / < 50 طلبًا |
| عناصر DOM | 2,039 | < 1,400 |
| Best Practices | 77 | ≥ 95 |
| Accessibility | 97 (2 أخطاء حرجة على صفحة المنتج) | 100 ودون أخطاء axe حرجة |
| SEO | 100 | 100 (حافظ عليه) |
| TTFB | 200ms | حافظ عليه |
| الضغط (20 اتصالًا / 30 ثانية) | 0 أخطاء، p99 = 140ms | حافظ عليه |

## 3. المطلوب في الموقع (بالأولوية)

### P0: قبل إطلاق أي حملة

**1. إيقاف الحركات المستمرة (السبب الأكبر لـTBT واستهلاك المعالج).**
- الشريط المتحرك `.tr` (عرضه 3093px)، وعناصر `pflux-rays`، وخلفية 3D والجزيئات: هذه تسبب نحو 60 إعادة تخطيط في الثانية والصفحة ساكنة.
- حوّل كل الحركات إلى `transform` و`opacity` فقط. لا تحرّك `left` أو `width` أو `scrollLeft`.
- أوقفها خارج الشاشة عبر `IntersectionObserver`، وعند `document.hidden`، ومع `prefers-reduced-motion`.
- خلفية 3D والصوت وأوضاع CINEMA وPS1 و8-BIT وANIME: لا تُحمَّل إلا عند ضغط المستخدم (lazy import)، ولا تُحمَّل أبدًا على الجوال عند التحميل الأول.
- **القبول:** حلقة CDP Performance.getMetrics لمدة 30 ثانية ساكنة تعطي LayoutCount أقل من 50.

**2. تقليل JavaScript وإزالة ما يحجب العرض.**
- `woo-custom-product-addons-pro/front-end.js` (93% غير مستخدم): حمّله في صفحات المنتج فقط، عبر Asset CleanUp أو Perfmatters أو `wp_dequeue_script` بشرط `is_product()`.
- `woocommerce-multi-currency-switcher.min.js` و`scrollBar.min.js` و`device.min.js`: أضف لها `defer`، أو احذفها إن لم تكن ضرورية.
- اجعل jQuery `defer` في LiteSpeed، مع اختبار Elementor وWoodmart بعد التغيير.
- Elementor `frontend.min.js` يسبب forced reflow لمدة 229ms: فعّل "Improved Asset Loading" و"Optimized DOM Output" و"Inline Font Icons" من Elementor → Features.
- الخطوط: استضف Tajawal وSpace Grotesk محليًا (WOFF2، subset عربي ولاتيني) مع `font-display: swap`، و`preload` للوزن 700 فقط، واحذف رابط Google Fonts.
- راجع Cloudflare Bot Fight / JS Challenge المفعّل على كل الموقع، فهو يضيف 0.43 ثانية من main.js. اجعله على `/wp-login.php` و`/my-account/` فقط.
- احذف preload لصورة `header-1.jpg` غير المستخدمة.

**3. ربط التتبع للإعلانات.**
- Meta Pixel موجود حاليًا (ID 1682679853414540). أضف **Meta Conversions API** من جهة الخادم مع إزالة التكرار (event_id) لأحداث: PageView وViewContent وAddToCart وInitiateCheckout وAddPaymentInfo وPurchase وContact (نقرة واتساب).
- أضف **GA4** عبر GTM، مع e-commerce events كاملة (view_item وadd_to_cart وbegin_checkout وpurchase) وتمرير العملة الصحيحة.
- أضف TikTok Pixel + Events API وSnap Pixel + CAPI، إن كانت الحملات ستعمل عليهما.
- اعتبر نقرة واتساب تحويلًا (Lead/Contact) في كل المنصات، لأن جزءًا كبيرًا من المبيعات يتم عبره.
- اربط كل ذلك بـWPConsent حتى لا يُطلق أي بكسل قبل الموافقة، وفعّل Consent Mode v2.
- **القبول:** Meta Events Manager يعرض Event Match Quality ≥ 7، وأحداث Purchase تصل من Pixel ومن CAPI مع إزالة التكرار، وGA4 DebugView يعرض كل الأحداث.

**4. واجهة الجوال فوق الطية.**
- أصلح الفراغ 15px على اليمين في كل المقاسات (العرض المتاح 345 من 360). السبب `scrollbar-gutter` أو scrollBar.js من Woodmart. اجعل `html, body { overflow-x: clip; }`، واحذف الحجز الإضافي.
- صغّر الهيرو على الجوال (الآن يأخذ قرابة 60% من الشاشة)، حتى تظهر حاسبة الكوينز وأزرار شراء/بيع داخل أول 600px.
- شريط الكوكيز (115px) وزر واتساب العائم يغطيان الحاسبة. اجعل الشريط مضغوطًا في سطر واحد، وارفع زر واتساب فوقه، ولا تغطِّ أي CTA.
- على سطح المكتب بارتفاع 900px، شريط التنقل السفلي والكوكيز يغطيان الحاسبة، فيجب إعادة توزيعهما.

### P1: خلال أسبوع

**5. الصور.**
- التوفير الممكن 870KB: قدّم أحجامًا مطابقة عبر `srcset`/`sizes`، وفعّل LiteSpeed Image Optimization أو Cloudflare Polish بصيغة WebP/AVIF.
- أضف `width` و`height` لكل `<img>` (61 من 74 بلا أبعاد)، والشعار أولًا.
- `flags-64.png` حجمه 64KB: استبدله بـSVG sprite أو بأعلام منفصلة صغيرة.
- الصور الأثقل:

  | الصورة | الحجم |
  |---|---|
  | player-21.webp | 166KB |
  | player-20.webp | 149KB |
  | fc27-icons-coins | 149KB |
  | playstation-games | 145KB |

  الهدف أقل من 60KB لكل منها.
- صورة الـLCP (إن تغيّرت من نص إلى صورة): `fetchpriority="high"` وبدون lazy.

**6. حجم HTML وDOM.**
- الرئيسية 581KB، والوسيط 419KB، وصفحة 404 وحدها 388KB.
- احذف CSS وSVG المضمّنة المكررة.
- اجعل صفحة 404 قالبًا خفيفًا.
- أزل الأقسام المخفية على الجوال بدل إخفائها بـCSS.
- اجعل القوائم الضخمة تُحمّل عند الطلب.
- CSS غير مستخدم 48KB (95%): فعّل UCSS في LiteSpeed مع استثناءات Woodmart.

**7. الكاش.**
- ترويسات HTML متعارضة: `max-age=300, must-revalidate` + `pragma: no-cache` + `expires: 1981`. وحّدها.
- الملفات الثابتة ذات الإصدار (`?ver=` و`/uploads/`): `Cache-Control: public, max-age=31536000, immutable`.
- Cloudflare: فعّل Cache Rules للـHTML للزوار غير المسجلين (Cache Everything + bypass على cookie `wordpress_logged_in`/`woocommerce_items_in_cart`)، أو APO.

**8. الخط العربي.**
- الـCSS يطلب `Cairo`، لكن المحمَّل فعليًا Tajawal 700 وSpace Grotesk 700 فقط، فالنصوص تظهر بخط الجهاز.
- اختر خطًا واحدًا للنص العربي (Tajawal 400/500/700 أو Cairo) وحمّله محليًا، ووحّد `font-family` في Woodmart وElementor Global Fonts.
- ارفع تباين التسميات الرمادية الباهتة في الحاسبة إلى 4.5:1 على الأقل.

**9. الأمان.**
- كوكي `PHPSESSID`: أضف `Secure` و`HttpOnly` (`session.cookie_secure=1` و`session.cookie_httponly=1` في php.ini أو .user.ini). ابحث عن الإضافة التي تبدأ `session_start()`.
- كوكيز `sbjs_*`: `Secure`.
- CSP: ابدأ بـ`Content-Security-Policy-Report-Only` مع `default-src 'self'`، و`script-src` للمضيفين المستخدمين (connect.facebook.net وgoogletagmanager.com وanalytics.tiktok.com وsc-static.net وstatic.cloudflareinsights.com وchallenges.cloudflare.com)، و`img-src 'self' data: https:`. ثم فعّلها بعد أسبوع بلا مخالفات.
- احذف `<meta name="generator">` وترويسة Link لـ`/wp-json/` ورابط shortlink.
- DNS: أضف سجل `CAA` (`0 issue "pki.goog"` و`letsencrypt.org` حسب Cloudflare)، وسجل `DMARC` (`v=DMARC1; p=quarantine; rua=mailto:...`)، وتحقق من DKIM لبريد mail.prinssfut.com.
- HSTS: أضف `preload` بعد التأكد من HTTPS في كل النطاقات الفرعية.
- تأكد أن Minimum TLS في Cloudflare هو 1.2 (يبدو حاليًا 1.3 فقط، وهذا قد يحجب أجهزة قديمة).
- **ممنوع:** تخزين EA backup codes أو كلمات مرور الحسابات في الطلبات أو الإيميلات بنص واضح. إن لزم Comfort Trade، فاجعل الحقل مشفّرًا ويُحذف تلقائيًا بعد تنفيذ الطلب.

**10. إمكانية الوصول (WCAG 2.1 AA).**
- ويدجت الحاسبة `.pcpe-widget[data-pid=1696]` فيه خطآن حرجان:
  - aria-allowed-attr ×4: سمات ARIA غير مسموحة على دور العنصر.
  - aria-required-children ×2: `role="tablist"` أو `listbox` بلا أبناء صحيحين.
  - الحل: استخدم `button` حقيقية مع `aria-pressed`، أو `radiogroup` مع `radio`.
- `.scb-product-whatsapp-button`: اجعل `aria-label` يبدأ بالنص الظاهر (label-content-name-mismatch).
- روابط التخطي `.wd-skip-navigation` و`.wd-skip-content`: أضف `id` الهدف و`tabindex="-1"`.
- الروابط داخل النص (ومنها wa.me في صفحة التواصل): ميّزها بخط سفلي، لا باللون وحده.
- ترتيب العناوين: H1 واحد، ثم H2، ثم H3 بلا قفز. في `/shop/ps-games/cod-mw3/` يوجد H1 مكرر.
- صور `alt=""` الـ23 (player-20 وplayer-21 وpf-mascot وps3d-logo): زخرفية؟ أبقها فارغة. معلوماتية؟ اكتب وصفًا عربيًا.

**11. SEO والبنية.**
- 35 صفحة وسوم وعلامات تجارية بلا وصف: اجعلها `noindex, follow` في Rank Math، أو اكتب لها أوصافًا.
- قاعدة التصنيف "العلامة التجارية" فيها مسافة (`%20`): غيّرها إلى `brand`، مع 301 من القديم.
- صحّح `/about-us/retrun-policy/` إلى `return-policy` مع 301. ادمج السياسات المتداخلة (الاسترجاع والإرجاع والإلغاء والشحن) في صفحتين واضحتين.
- `/سياسة-الخصوصية/` مكررة مع `/about-us/privacy-policy/`: أبقِ واحدة، و301 للأخرى، واحذفها من sitemap.
- `/product-category/fut-champions/` يحوّل إلى منتج: أعده تصنيفًا أو حدّث الروابط الداخلية.
- الروابط الداخلية إلى `?per_page=15/35/50`: أزلها من القوالب، أو اجعلها `nofollow`.
- حتى 85 صفحة يتيمة (مقالات وjoin-us وغيرها): اربطها من القوائم والتصنيفات ومقالات ذات صلة.
- `http://www` يمر بتحويلين: اجعله تحويلًا واحدًا إلى `https://prinssfut.com/` عبر Cloudflare Redirect Rule.
- Schema:
  - أضف `Product` مع `offers` لكل منتج، والسعر بالعملة الصحيحة.
  - أضف `AggregateRating`/`Review` **فقط إن كانت التقييمات حقيقية وموثقة** (اربطها بتقييمات WooCommerce أو Google).
  - أضف `FAQPage` لصفحة الأسئلة.
- `og:locale`: غيّره من `ar_AR` إلى `ar_KW` أو `ar`.
- أزل تسمية Twitter "reading time" من الرئيسية.

**12. التحويل (CRO) والثقة.**
- الأسعار تظهر بفاصلة أوروبية (`$6,50`): غيّر WooCommerce → Settings → فاصل الكسور إلى `.` والآلاف إلى `,`، وطبّق ذلك على Multi Currency.
- اعرض السعر بعملة الزائر تلقائيًا حسب الدولة (geo من Cloudflare `CF-IPCountry`).
- أضف شارات ثقة قرب زر الشراء: وقت التسليم المتوقع، وضمان، وطرق الدفع، وعدد الطلبات المنفذة (إن كان حقيقيًا).
- أضف تقييمات Google Business أو Trustpilot حقيقية، وروابط لإثباتات Instagram موجودة حاليًا.
- أضف `tel:` و`mailto:` في التذييل وصفحة التواصل.
- صفحات هبوط للحملات (بلا قائمة علوية مشتتة، وCTA واحد، وسرعة عالية):
  - `/lp/ps-cards/`
  - `/lp/fc-points/`
  - `/lp/games/`

### P2: مراقبة وتحسين مستمر

**13.** ثبّت Microsoft Clarity (مجاني) لخرائط الحرارة وتسجيل الجلسات، مربوطًا بالموافقة على الكوكيز.

**14.** تتبع الأخطاء: Sentry Browser أو Cloudflare Web Analytics + Query Monitor في بيئة staging.

**15.** المراقبة: UptimeRobot أو Better Stack كل دقيقة على الرئيسية و`/checkout/`، مع تنبيه واتساب أو بريد.

**16.** قاعدة البيانات: افحص بـQuery Monitor صفحات المنتج والسلة والدفع.
- نظّف `wp_options` من autoload الذي يتجاوز 1MB، وحدّث transients.
- فعّل Redis Object Cache إن توفر في الاستضافة.
- راجع جداول `wp_wc_orders` وفهارسها (HPOS).

**17.** اختبر على Firefox وSafari iOS، لأن الفحص الحالي تم على Chromium فقط.

**18.** بيئة staging: كل التعديلات تُختبر على نسخة staging أولًا، مع نسخة احتياطية كاملة قبل كل تغيير في الإنتاج.

## 4. المطلوب في تطبيق Android (المستودع)

ما تم في PR #1:
- توحيد `WHATSAPP_NUMBER`.
- حذف السلة والطلبات الوهمية.
- زر "Send Order via WhatsApp".
- حذف حقل EA Backup Code.
- R8 و`allowBackup=false`.
- حذف الصور المكررة.

المطلوب بعده:

1. **ابنِ نسخة release واختبرها بالكامل** بعد تفعيل R8. أضف قواعد `proguard-rules.pro` لـMoshi وRetrofit وFirebase إن ظهرت أعطال.
2. **اربط التطبيق بمتجر WooCommerce الحقيقي**، بدل الأسعار والمنتجات المكتوبة يدويًا في `MainViewModel.kt` و`GamingScreen.kt` و`BuyCoinsScreen.kt`:
   - استخدم WooCommerce REST API (`/wp-json/wc/v3/products`) عبر Retrofit المضاف أصلًا.
   - لا تضع consumer key/secret داخل التطبيق. مرّر الطلبات عبر endpoint وسيط آمن (Cloudflare Worker أو Firebase Function).
   - الأسعار وأسعار الصرف (`Currency.rateToEgp` مكتوبة يدويًا الآن) تأتي من الخادم.
3. **إنشاء الطلبات فعليًا:** `createOrder` الآن يحفظ في الذاكرة فقط. أنشئ الطلب عبر API ليظهر في لوحة WooCommerce، واحفظ سجل الطلبات محليًا بـRoom (مضاف وغير مستخدم)، واجلب الحالة من `/track-order/`.
4. **الدفع:** إما فتح صفحة checkout الموقع في Custom Tab، أو دمج بوابة (MyFatoorah أو Tap أو KNET أو Paymob) عبر الخادم.
5. **أكواد الخصم:** احذف `PRINSS27` و`FUT27` و`VIP` من `MainViewModel.applyPromoCode`، وتحقق منها عبر WooCommerce Coupons API.
6. **التعريب:** الواجهة إنجليزية والجمهور عربي.
   - انقل كل النصوص إلى `strings.xml` مع `values-ar`، واجعل العربية افتراضية.
   - تأكد من RTL في كل الشاشات.
   - التواريخ والأرقام عبر `Locale` المستخدم.
7. **إعادة الهيكلة:**
   - قسّم الملفات الضخمة (`FUTCard.kt` بـ895 سطرًا، و`PrinssFutHomeSections.kt` بـ821، و`HomeScreen.kt` بـ756، و`BuyCoinsScreen.kt` بـ748).
   - أضف Navigation Compose بدل التبديل اليدوي للتبويبات.
   - غيّر `namespace` و`package` من `com.example` إلى `com.prinssfut.app`.
8. **الأداء:**
   - الشريط المتحرك `PrinssFutHomeSections.kt` (`while(true)` + `animateScrollTo`) وتأثيرات `AtmosphereEffects`: أوقفها خارج الشاشة وفي وضع توفير الطاقة.
   - حوّل الصور JPG الكبيرة (0.5 إلى 0.9MB) إلى WebP بجودة 80.
9. **التبعيات:**
   - احذف غير المستخدم، أو استخدمه فعلًا: Room وRetrofit وMoshi وFirebase AI.
   - انقل `firebase-appcheck-debug` إلى `debugImplementation` فقط، واستخدم Play Integrity في release.
10. **إمكانية الوصول:**
    - `contentDescription` لكل أيقونة تفاعلية، وحاليًا 5 منها `null`.
    - أهداف لمس ≥ 48dp.
    - دعم TalkBack وتكبير الخط.
11. **التتبع:**
    - Firebase Analytics + Crashlytics.
    - Meta App Events وTikTok Events SDK بنفس أسماء أحداث الموقع.
    - deep links من الإعلانات إلى المنتج.
12. **الاختبارات:** Unit tests للحسابات (`calculateCoinPrice` والخصم والإجمالي)، وCompose UI tests لمسار السلة والطلب، وCI بـGitHub Actions (`./gradlew lint test assembleRelease`).

## 5. ملاحظة سياسات الإعلانات (مهم)

- Meta وGoogle وTikTok تقيّد أو ترفض إعلانات بيع عملات داخل الألعاب (FC Coins)، لأنها تخالف شروط EA.
- **وجّه الإعلانات المدفوعة إلى المنتجات المسموحة:** بطاقات PlayStation وXbox الرسمية، وFC Points، والألعاب الرقمية.
- اجعل صفحات الهبوط لهذه المنتجات خالية من ذكر بيع الكوينز.
- اعتمد على العملاء الحاليين وإعادة الاستهداف لخدمة الكوينز بحذر، وراجع سياسة كل منصة قبل الإطلاق.

## 6. طريقة التسليم

- نفّذ بالأولوية P0 ثم P1 ثم P2. بعد كل بند، اكتب ما تغيّر وكيف تحققت منه.
- بعد P0 وP1، أعد قياس Lighthouse على الجوال 3 مرات (الوسيط) وPageSpeed Insights، وقارن بجدول القسم 2.
- شغّل axe-core على: الرئيسية، و`/shop/ea-fc-27-coins/buy-ps-coins/`، و`/product-category/ea-fc-27-coins/`، و`/about-us/contact-us/`.
- اختبر شراءً تجريبيًا كاملًا، وتأكد من وصول Purchase إلى Meta (Pixel + CAPI) وGA4.
- لا تغيّر الهوية البصرية (الثيم الداكن النيون، والتصميم RTL) ولا روابط المنتجات الحالية بلا تحويل 301.
- سلّم قائمة بكل الإضافات التي أُضيفت أو حُذفت، وبكل إعداد تغيّر في Cloudflare وLiteSpeed.
