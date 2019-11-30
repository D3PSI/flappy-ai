#!/usr/bin/env bash
echo "Thank you for downloading this project!"
echo "Verifying user..."
if [[ $EUID -ne 0 ]]; then
    echo "Installing dependencies..."
    echo "Installing SDKMAN! and gradle 6.0..."
    curl -s "https://get.sdkman.io" | bash
    source "$HOME/.sdkman/bin/sdkman-init.sh"
    sdk install gradle 6.0
    echo "Building project flappy-ai..."
    gradle build
    echo "Copying files to destination directory..."
    [ -d $HOME/flappy-ai ] || mkdir $HOME/flappy-ai
    cp -R flappy-ai res/ build/ $HOME/flappy-ai
    echo "flappy-ai can now be found at $HOME/flappy-ai"
    echo "Exporting to PATH..."
    var='$PATH'
    echo -e "\nexport PATH=$var:$HOME/flappy-ai" >> $HOME/.bashrc
    echo "Your new PATH is now: "
    echo $PATH
    exit 0
else
   echo "This script must not be run as root" 1>&2
   exit 1
fi