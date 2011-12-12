<?php
/***************************************************************************
 *                               fundation.php
 *                            -------------------
 *   Directory		 : www-etu/buckutt
 *   Begin            : Sunday, Mar 14, 2009
 *   Copyright        : (C) 2005 UTT Net Group
 *   Licence		     : GNU GPL
 *   Email            : ung@utt.fr
 *   Version		     : 7.0
 *
 *
 ***************************************************************************/

 
 /***************************************************************************
 *
 *   This program is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU General Public License as
 *   published by the Free Software Foundation; either version 2 of the
 *   License, or (at your option) any later version.
 *
 *   ---------------------------------------------------
 *
 *   http://www.gnu.org/copyleft/gpl.html
 *
 ***************************************************************************/

 
 /***************************************************************************
 *
 *   Log de modification
 *
 *   -----------------------------------------------------------------------
 *
 *   Nom			Date 			Commentaire
 *
 *
 *
 *
 *
 ***************************************************************************/
 
//Obligatoire
$idPage = 160;
$needSBUY=false;
$needFADMIN=false;
$needBADMIN=true;

include_once('header.inc.php');


// S'il y a un get de l'id fundation le SADMIN et le BADMIN sont sérialisés dans la session
if (isset($_SESSION['buckutt']['SADMIN']) && isset($_SESSION['buckutt']['BADMIN'])) {

  // On délinéarise les objets SADMIN et BADMIN correspondant à l'organisme stockés.
  $SADMIN = unserialize($_SESSION['buckutt']['SADMIN']);
  $BADMIN = unserialize($_SESSION['buckutt']['BADMIN']);

  // Header de l'IHM
?>
<div class="colomn1">
<?php 
  $edit = false;
  // Ajout, edition ou suppression d'une categorie
  if (!empty($_POST)) {
  	// On verifie si on est en suppresion
    if (isset($_POST['id']) && isset($_POST['delete'])) {
      // On test la suppression
      $test = $BADMIN->deleteFundation($_POST['id']);
      if(!empty($test) && $test==true) {
        $page->msgInfo('Organisme supprim&eacute; avec succ&egrave;s !');
      } else {
        $page->msgError('Erreur : la suppression n\'a pas pu avoir lieu...');
      }
    } else {
      // On test si tous les post sont remplis
      if (isset($_POST['name']) && !empty($_POST['name'])) {
        $name = htmlentities(utf8_decode($_POST['name']));
        // On verifie si on est en ajout ou en edition (s'il y a id_object existant)
        if (isset($_POST['id'])) {
          // On test l'edition
          if (isset($_POST['edit_fundation'])) {
            $test_name = $BADMIN->editFundationName($_POST['id'], $name);
            if(!empty($test_name) && $test_name==true) {
              $page->msgInfo('Organisme édit&eacute; avec succ&egrave;s !');
            } else { $page->msgError('Erreur : l\'édition n\'a pas pu avoir lieu...'); }
          } else { $edit = true; }
        } else {
          // On test l'ajout
          $test = $BADMIN->addFundation($name);
          if(!empty($test) && $test==true) {
            $page->msgInfo('Organisme ajout&eacute; avec succ&egrave;s !');
          } else { $page->msgError('Erreur : l\'ajout n\'a pas pu avoir lieu...'); }
        }
      } else { $page->msgError('Attention : il faut remplir tous les champs. Surtout quand il n\'y en a qu\'un ;-)'); }
    }
  }

  // On affiche ajouter ou editer
  if ($edit){
    $page->moduleHeader('Editer un organisme', 'FFCC00');
  } else {
    $page->moduleHeader('Ajouter un organisme', 'FFCC00');
  }

  echo '<form method="post" action="fundation.php" >';
?>
		<table width="100%">
			<tr>
				<td>Nom :</td>
				<td><input type="text" name="name" id="name" maxlength="50" size="50" value="<?php if($edit) {echo $_POST['name']; }?>" /></td>
			</tr>
        	<tr>
            	<td colspan="2" align="right">
            		<input type="<?php if($edit){ 
            	    	echo 'hidden" name="id" value="'.$_POST['id'].'" />
            	    	<input type="button" onClick="location.href=\'\'" name="stop_edit" value="Stopper l\'édition" />
            	    	<input type="submit" id="submit" name="edit_fundation" value="Editer cet';
            	    } else { echo 'submit" id="submit" name="add_categorie" value="Ajouter un'; } ?> organisme" />
 	           </td>
    	    </tr>
		</table>
	</form>
<?php
  $page->modulefooter();

  $page->moduleHeader('Les organismes');
  $j='0'; // Compteur utilisé pour l'alternance des couleurs des lignes du tableau

  if ($allFundations = $BADMIN->getAllFundations()) {
    echo '<table width="100%">
      <tr>
        <th>Nom</th>
        <th width="20px"></th>
        <th width="20px"></th>
      </tr>';
    foreach (str_getcsv($allFundations, ',') as $fundation) {
      /* DESCRIPTION DE $fundation
       * 0 = Identifiant de l'organisme
       * 1 = Nom de l'organisme
       */
      echo '<tr class="'.((++$j%2)?'pair':'impair').'">
          <td>'.$fundation[1].'</td>';
      echo '<td><form style="display:inline;" method="post" action="fundation.php">
            <input type="hidden" name="id" value="'.$fundation[0].'" />
            <input type="hidden" name="name" value="'.$fundation[1].'" />
            <input class="edit" type="submit" name="edit" title="Editer" value="" />
          </form></td>';
      // Formulaire de suppression d'un organisme  
      echo '<td><form style="display:inline;" method="post" action="fundation.php">
          <input type="hidden" name="id" value="'.$fundation[0].'" />
          <input class="delete" type="submit" name="delete" title="Supprimer" value="" />
        </form></td>
      </tr>';
    }
  } else {
    echo "Tu n'as enregistré aucun organisme.";
  }

  echo '</table>';
  $page->modulefooter();
?>
	</div>
	<div class="colomn2">
<?php  include_once ('navigation.inc.php'); ?>
	</div>
<?php 
  $page->footer();
} else {
  header('Location: index.php');
}
?>
