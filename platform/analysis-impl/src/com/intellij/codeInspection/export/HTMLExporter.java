/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Created by IntelliJ IDEA.
 * User: max
 * Date: Jan 20, 2002
 * Time: 9:50:29 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package com.intellij.codeInspection.export;

import com.intellij.codeInspection.ex.HTMLComposerImpl;
import com.intellij.codeInspection.reference.RefEntity;
import com.intellij.util.containers.HashMap;
import org.jetbrains.annotations.NonNls;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HTMLExporter {
  private final String myRootFolder;
  private int myFileCounter;
  private final Map<RefEntity,String> myElementToFilenameMap;
  private final HTMLComposerImpl myComposer;
  private final Set<RefEntity> myGeneratedReferences;

  public HTMLExporter(String rootFolder, HTMLComposerImpl composer) {
    myRootFolder = rootFolder;
    myElementToFilenameMap = new HashMap<>();
    myFileCounter = 0;
    myComposer = composer;
    myGeneratedReferences = new HashSet<>();
  }

  public String createPage(RefEntity element) {
    StringBuffer buf = new StringBuffer();
    myComposer.composeWithExporter(buf, element, this);
    return buf.toString();
  }

  //TODO delete all these methods

  public String getURL(RefEntity element) {
    myGeneratedReferences.add(element);
    return fileNameForElement(element);
  }

  public String fileNameForElement(RefEntity element) {
    @NonNls String fileName = myElementToFilenameMap.get(element);

    if (fileName == null) {
      fileName = "e" + Integer.toString(++myFileCounter) + ".html";
      myElementToFilenameMap.put(element, fileName);
    }

    return fileName;
  }

  public String getRootFolder() {
    return myRootFolder;
  }
}
