# ऑडियो ट्रांसकोडर-इनक्यूबेटिंग
============

## परिचय

यह इंजन ऑडियो फ़ाइलों को ट्रांसकोड करने के लिए [Xuggler](http://www.xuggle.com/xuggler/) लाइब्रेरी का उपयोग करता है। Xuggler [GPL संस्करण 3 लाइसेंस](https://www.gnu.org/copyleft/gpl.html) के तहत उपलब्ध है। यह आवश्यक है क्योंकि पूर्व-संकलित संस्करण H264 वीडियो एन्कोडिंग के लिए X264 वितरित करता है।

## उपयोग

यह इंजन ऑडियो फ़ाइलों (mp3, aac, wav, आदि) को निम्नलिखित प्रारूप में परिवर्तित करता है:

```
आवृत्ति (Frequency): 16 kHz
गहराई (Depth): 16 bit
प्रकार (Type): mono
बाइट क्रम: little-endian
```

ContentItem के माध्यम से पार्स किए गए ऑडियो स्ट्रीम को उपरोक्त प्रारूप में परिवर्तित किया जाता है और स्ट्रीम के रूप में [SpeechToText Enhancement Engine](https://github.com/sumansaurabh/SpeechToTextEngine) को भेजा जाता है।

## तकनीकी विवरण

### समर्थित ऑडियो प्रारूप
- MP3
- AAC
- WAV
- अन्य सामान्य ऑडियो प्रारूप

### आउटपुट विनिर्देश
- **नमूना दर**: 16 kHz
- **बिट गहराई**: 16-bit
- **चैनल**: मोनो (एकल चैनल)
- **बाइट क्रम**: Little-endian

## स्थापना

यह एक Maven आधारित Java परियोजना है। परियोजना को बनाने के लिए:

```bash
mvn clean install
```

## निर्भरताएँ

- Java Development Kit (JDK)
- Apache Maven
- Xuggler लाइब्रेरी

## लाइसेंस

यह परियोजना Apache Software License, Version 2.0 के तहत लाइसेंस प्राप्त है।

Xuggler घटक GPL Version 3 लाइसेंस के तहत है।

## नोट

⚠️ **महत्वपूर्ण**: यह परियोजना विकास के अधीन है। आपको कई समस्याओं का सामना करना पड़ सकता है।

## योगदान

योगदान का स्वागत है! कृपया परियोजना में योगदान देने से पहले लाइसेंस शर्तों की समीक्षा करें।

## संपर्क और समर्थन

अधिक जानकारी के लिए, कृपया [Apache Stanbol](http://stanbol.apache.org/) वेबसाइट देखें।

## संबंधित परियोजनाएँ

- [SpeechToText Enhancement Engine](https://github.com/sumansaurabh/SpeechToTextEngine)
- [Apache Stanbol](http://stanbol.apache.org/)

---

**Apache Stanbol का हिस्सा**

यह परियोजना Apache Stanbol पारिस्थितिकी तंत्र का हिस्सा है, जो सिमेंटिक सामग्री प्रबंधन के लिए पुन: प्रयोज्य घटक प्रदान करता है।
