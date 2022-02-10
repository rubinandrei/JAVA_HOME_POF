
## Before:
### result: Min ~ -Xms1G -Xmx1G

### -Xms512 -Xmx512 ***
```heapdump.hprof```

### -Xms512m -Xmx512m
```
1. spend msec:25172, sec:25
2. spend msec:17586, sec:17
3. spend msec:14741, sec:14
```

### -Xms768m -Xmx768m
```
1. spend msec:19715, sec:19
2. spend msec:14743, sec:14
3. spend msec:13271, sec:13
```

### -Xms1G -Xmx1G
```
1. spend msec:13084, sec:13
2. spend msec:13069, sec:12
3. spend msec:12974, sec:12
```

### -Xms1280m -Xmx1280m
```
1. spend msec:12984, sec:12
2. spend msec:12500, sec:12
3. spend msec:12378, sec:12
```

### -Xms1536m -Xmx1536m
```
1. spend msec:12858, sec:12
2. spend msec:12538, sec:12
3. spend msec:12205, sec:12
```

### -Xms2G -Xmx2G
```
spend msec:12637, sec:12
spend msec:12876, sec:12
spend msec:12691, sec:12
```


## After:

### -Xms128m -Xmx128m
```
spend msec:847, sec:0
spend msec:1652, sec:1
spend msec:835, sec:0
```

### -Xms256m -Xmx256m
```
spend msec:974, sec:0
spend msec:1294, sec:1
spend msec:850, sec:0
```
