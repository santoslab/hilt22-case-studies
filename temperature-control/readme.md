# Temperature Control

***TODO: create and autogen readme (e.g. autogen [readme](https://github.com/santoslab/hamr-case-examples/tree/master/case/tool-assessment-4/basic/test_data_port_periodic_domains))***

## Sporadic

### AADL Model

***TODO: Arch diagram***

[TempControlSoftwareSystem.s](aadl/packages/TempControlSoftwareSystem.aadl#L61)

### Slang Code

[hamr-gen-sporadic/slang](hamr-gen-sporadic/slang)

***TODO: Link to component behavior code for each component***

### How to Build/Verify

***TODO: Sireum install instructions, link to phantom script***

```
$SIREUM_HOME/bin/sireum proyek logika --all --rlimit 1700000 --timeout 3000 ./hamr-gen-sporadic/slang
```

## Periodic

### AADL Model

***TODO: Arch diagram***

[TempControlSoftwareSystem.p](aadl/packages/TempControlSoftwareSystem.aadl#L288)

### Slang Code

[hamr-gen-periodic/slang](hamr-gen-periodic/slang)

***TODO: Link to component behavior code for each component***

### How to Build/Verify

***TODO: Sireum install instructions, link to phantom script***

```
$SIREUM_HOME/bin/sireum proyek logika --all --rlimit 1700000 --timeout 3000 ./hamr-gen-periodic/slang
```