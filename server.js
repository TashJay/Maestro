const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 5000;
const APK_PATH = path.join(__dirname, '.build-outputs', 'app-debug.apk');

const html = `<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Teddy Cabs</title>
  <style>
    * { box-sizing: border-box; margin: 0; padding: 0; }
    body {
      background: #0a0a0a;
      color: #f0e6c8;
      font-family: 'Georgia', serif;
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 40px 20px;
    }
    .container {
      max-width: 700px;
      width: 100%;
      text-align: center;
    }
    .badge {
      display: inline-block;
      background: #c9a227;
      color: #0a0a0a;
      font-size: 11px;
      font-weight: bold;
      letter-spacing: 2px;
      text-transform: uppercase;
      padding: 6px 16px;
      border-radius: 20px;
      margin-bottom: 24px;
    }
    h1 {
      font-size: 52px;
      color: #c9a227;
      letter-spacing: 3px;
      margin-bottom: 8px;
    }
    .tagline {
      font-size: 18px;
      color: #a0906a;
      font-style: italic;
      margin-bottom: 40px;
    }
    .divider {
      border: none;
      border-top: 1px solid #c9a22733;
      margin: 32px 0;
    }
    .description {
      font-size: 16px;
      line-height: 1.8;
      color: #c8b88a;
      margin-bottom: 32px;
    }
    .features {
      display: grid;
      grid-template-columns: 1fr 1fr;
      gap: 16px;
      margin-bottom: 40px;
      text-align: left;
    }
    .feature {
      background: #151515;
      border: 1px solid #c9a22722;
      border-radius: 8px;
      padding: 16px 20px;
    }
    .feature .icon { font-size: 22px; margin-bottom: 8px; }
    .feature h3 { font-size: 14px; color: #c9a227; margin-bottom: 4px; }
    .feature p { font-size: 13px; color: #8a7a5a; line-height: 1.5; }
    .download-btn {
      display: inline-block;
      background: linear-gradient(135deg, #c9a227, #e8c84a);
      color: #0a0a0a;
      text-decoration: none;
      font-size: 16px;
      font-weight: bold;
      letter-spacing: 1px;
      padding: 16px 48px;
      border-radius: 4px;
      transition: opacity 0.2s;
      margin-bottom: 12px;
    }
    .download-btn:hover { opacity: 0.85; }
    .download-note {
      font-size: 12px;
      color: #6a5a3a;
      margin-top: 8px;
    }
    .tech-stack {
      display: flex;
      gap: 10px;
      justify-content: center;
      flex-wrap: wrap;
      margin-top: 32px;
    }
    .tech-tag {
      background: #1a1a1a;
      border: 1px solid #333;
      color: #8a8a8a;
      font-size: 12px;
      padding: 4px 12px;
      border-radius: 20px;
    }
    .android-note {
      background: #111;
      border: 1px solid #c9a22744;
      border-radius: 8px;
      padding: 20px;
      margin-top: 40px;
      font-size: 13px;
      color: #7a6a4a;
      line-height: 1.7;
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="badge">Android App</div>
    <h1>TEDDY CABS</h1>
    <p class="tagline">Premium ride hailing service with black and gold elegance</p>

    <hr class="divider" />

    <p class="description">
      A full-featured ride-hailing mobile application for Android, supporting both
      passenger and driver roles with real-time ride tracking, secure M-Pesa payments,
      and an AI-powered assistant via Google Gemini.
    </p>

    <div class="features">
      <div class="feature">
        <div class="icon">🚗</div>
        <h3>Ride Booking</h3>
        <p>Book rides instantly with real-time driver matching and live tracking</p>
      </div>
      <div class="feature">
        <div class="icon">👤</div>
        <h3>Dual Roles</h3>
        <p>Separate dashboards for passengers and drivers with tailored experiences</p>
      </div>
      <div class="feature">
        <div class="icon">💳</div>
        <h3>M-Pesa Payments</h3>
        <p>Secure in-app payments via M-Pesa mobile money integration</p>
      </div>
      <div class="feature">
        <div class="icon">🤖</div>
        <h3>AI Assistant</h3>
        <p>Google Gemini-powered customer support and ride management</p>
      </div>
      <div class="feature">
        <div class="icon">🗺️</div>
        <h3>Google Maps</h3>
        <p>Integrated Google Maps for navigation and route planning</p>
      </div>
      <div class="feature">
        <div class="icon">⭐</div>
        <h3>Rating System</h3>
        <p>Rate drivers and passengers to maintain service quality</p>
      </div>
    </div>

    <a href="/download-apk" class="download-btn">⬇ Download APK (Debug Build)</a>
    <p class="download-note">Install on any Android device (API 24+, Android 7.0+)</p>

    <div class="tech-stack">
      <span class="tech-tag">Kotlin</span>
      <span class="tech-tag">Jetpack Compose</span>
      <span class="tech-tag">Material Design 3</span>
      <span class="tech-tag">Google Maps</span>
      <span class="tech-tag">Firebase</span>
      <span class="tech-tag">Room DB</span>
      <span class="tech-tag">Retrofit</span>
      <span class="tech-tag">Gemini AI</span>
    </div>

    <div class="android-note">
      <strong style="color:#c9a227">Native Android App</strong><br/>
      This is an Android application built with Kotlin &amp; Jetpack Compose. To run it on an emulator,
      open the project in Android Studio. To run on a device, download and install the APK above
      (you may need to enable "Install from unknown sources" in your Android settings).
    </div>
  </div>
</body>
</html>`;

const server = http.createServer((req, res) => {
  if (req.url === '/download-apk') {
    if (fs.existsSync(APK_PATH)) {
      const stat = fs.statSync(APK_PATH);
      res.writeHead(200, {
        'Content-Type': 'application/vnd.android.package-archive',
        'Content-Disposition': 'attachment; filename="teddy-cabs-debug.apk"',
        'Content-Length': stat.size,
      });
      fs.createReadStream(APK_PATH).pipe(res);
    } else {
      res.writeHead(404);
      res.end('APK not found');
    }
  } else {
    res.writeHead(200, { 'Content-Type': 'text/html; charset=utf-8' });
    res.end(html);
  }
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Teddy Cabs info page running at http://0.0.0.0:${PORT}`);
});
