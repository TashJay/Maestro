const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 5000;

const MIME = {
  '.html': 'text/html; charset=utf-8',
  '.css':  'text/css',
  '.js':   'application/javascript',
  '.png':  'image/png',
  '.jpg':  'image/jpeg',
  '.svg':  'image/svg+xml',
  '.ico':  'image/x-icon',
  '.apk':  'application/vnd.android.package-archive',
};

function serveFile(res, filePath, headers = {}) {
  const ext = path.extname(filePath);
  const contentType = MIME[ext] || 'text/plain';
  fs.readFile(filePath, (err, data) => {
    if (err) {
      res.writeHead(404, { 'Content-Type': 'text/html' });
      res.end('<h2 style="font-family:sans-serif;padding:40px;color:#c9a227;">404 — Not Found</h2>');
      return;
    }
    res.writeHead(200, { 'Content-Type': contentType, ...headers });
    res.end(data);
  });
}

const ROUTES = {
  '/':           path.join(__dirname, 'views', 'home.html'),
  '/passenger':  path.join(__dirname, 'views', 'passenger.html'),
  '/driver':     path.join(__dirname, 'views', 'driver.html'),
  '/admin':      path.join(__dirname, 'views', 'admin.html'),
};

const APK_PATH = path.join(__dirname, '.build-outputs', 'app-debug.apk');

const server = http.createServer((req, res) => {
  const url = req.url.split('?')[0];

  // APK download
  if (url === '/download-apk') {
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
    return;
  }

  // Static assets (css, js, images)
  if (url.startsWith('/css/') || url.startsWith('/js/') || url.startsWith('/assets/')) {
    serveFile(res, path.join(__dirname, 'public', url));
    return;
  }

  // Page routes
  if (ROUTES[url]) {
    serveFile(res, ROUTES[url]);
    return;
  }

  // 404
  res.writeHead(404, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(`<!DOCTYPE html>
<html><head><title>404</title><link rel="stylesheet" href="/css/style.css"></head>
<body style="display:flex;align-items:center;justify-content:center;min-height:100vh;text-align:center;">
  <div>
    <div style="font-size:72px;color:var(--gold);font-weight:800;">404</div>
    <p style="color:var(--text-muted);margin:12px 0 24px;">Page not found</p>
    <a href="/" class="btn btn-gold" style="display:inline-block;padding:12px 32px;background:var(--gold);color:#000;border-radius:8px;font-weight:700;text-decoration:none;">← Go Home</a>
  </div>
</body></html>`);
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`Teddy Cabs running at http://0.0.0.0:${PORT}`);
  console.log(`  / .............. Home`);
  console.log(`  /passenger ...... Passenger App`);
  console.log(`  /driver ......... Driver App`);
  console.log(`  /admin .......... Admin Dashboard`);
  console.log(`  /download-apk ... APK Download`);
});
