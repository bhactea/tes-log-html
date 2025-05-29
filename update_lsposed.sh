#!/bin/bash

echo "🔍 Mengecek dan merapikan struktur repository LSPosed..."

# Hapus module.prop yang salah
if [ -f "app/module.prop" ]; then
    echo "❌ Menghapus file duplikat: app/module.prop"
    git rm -f app/module.prop
fi

# Pastikan module.prop yang benar ada
if [ ! -f "app/src/main/assets/module.prop" ]; then
    echo "⚠️  module.prop yang benar belum ada, membuat baru..."
    mkdir -p app/src/main/assets
    cat <<EOF > app/src/main/assets/module.prop
id=com.harpa.logger
name=Harpa Logging Hook
version=1.0
versionCode=1
author=bhactea
description=Modul logging untuk WebView dan Harpa activity
EOF
fi

# Tambahkan perubahan
echo "📦 Menambahkan semua perubahan"
git add .

# Commit
echo "📝 Commit..."
git commit -m "🧹 Cleanup module.prop & ensure correct structure"

# Push
echo "🚀 Push ke GitHub..."
git push origin main

echo "✅ Selesai. Struktur LSPosed sudah rapi & terkini."
