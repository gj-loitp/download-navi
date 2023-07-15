package com.roy.downloader.core.system;

import android.system.Os;
import android.system.OsConstants;
import android.system.StructStatVfs;

import androidx.annotation.NonNull;

import java.io.FileDescriptor;
import java.io.IOException;

class SysCallImpl implements SysCall {
    @Override
    public void lseek(@NonNull FileDescriptor fd, long offset) throws IOException, UnsupportedOperationException {
        try {
            Os.lseek(fd, offset, OsConstants.SEEK_SET);

        } catch (Exception e) {
            throw new IOException(e);
        }

    }

    @Override
    public void fallocate(@NonNull FileDescriptor fd, long length) throws IOException {

        try {
            long curSize = Os.fstat(fd).st_size;
            long newBytes = length - curSize;
            long availBytes = availableBytes(fd);
            if (availBytes < newBytes)
                throw new IOException("Not enough free space; " + newBytes + " requested, " +
                        availBytes + " available");

            Os.posix_fallocate(fd, 0, length);

        } catch (Exception e) {
            try {
                Os.ftruncate(fd, length);

            } catch (Exception ex) {
                throw new IOException(ex);
            }
        }
    }

    /*
     * Return the number of bytes that are free on the file system
     * backing the given FileDescriptor
     *
     * TODO: maybe there is analog for KitKat?
     */

    @Override
    public long availableBytes(@NonNull FileDescriptor fd) throws IOException {
        try {
            StructStatVfs stat = Os.fstatvfs(fd);

            return stat.f_bavail * stat.f_bsize;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
