#!/bin/bash

# HumansPlus Mod - Build and Test Script for Minecraft 1.12.2

echo "==============================================="
echo "HumansPlus Mod - Build Script for MC 1.12.2"
echo "==============================================="

# Set up environment
export PATH=$PATH:/workspaces/HumansPlus/gradle-4.9/bin

# Check Java version (Minecraft 1.12.2 works with Java 8-11)
echo "Checking Java version..."
java -version

# Setup ForgeGradle workspace
echo "Setting up Forge development workspace..."
gradle setupDecompWorkspace --refresh-dependencies

# Compile the mod
echo "Compiling the mod..."
gradle build

# If successful, show output location
if [ $? -eq 0 ]; then
    echo "==============================================="
    echo "✅ BUILD SUCCESSFUL!"
    echo "==============================================="
    echo "Mod JAR file will be located at:"
    echo "build/libs/humansplus-1.12.2-2.0.0.jar"
    echo ""
    echo "To test the mod:"
    echo "1. Copy the JAR to your Minecraft 1.12.2 mods folder"
    echo "2. Launch Minecraft 1.12.2 with Forge"
    echo "3. Look for 'Humans+' in the mod list"
    echo ""
    echo "Features to test:"
    echo "- Human NPCs spawning in various biomes"
    echo "- Quest system (default key: probably Q)"
    echo "- Custom swords with special abilities"
    echo "- Human combat and AI behavior"
else
    echo "==============================================="
    echo "❌ BUILD FAILED"
    echo "==============================================="
    echo "Check the error messages above for issues."
    echo "Common issues:"
    echo "- Missing Java 8 (required for MC 1.12.2)"
    echo "- Network issues downloading dependencies"
    echo "- Missing or incorrect ForgeGradle setup"
fi
