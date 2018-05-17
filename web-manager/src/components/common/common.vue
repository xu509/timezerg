<script>
const table_page_node = 1;
const table_size_node = 10;
const doamin = "http://127.0.0.1:8081";

const url_node_list = doamin + "/node/list";
const url_node_add = doamin + "/node/save";
const url_node_edit_init = doamin + "/node/edit/init";
const url_node_edit_save = doamin + "/node/edit/submit";
const url_civilization_list = doamin + "/civilization/list";
const url_civilization_add = doamin + "/civilization/save";
const url_civilization_edit_init = doamin + "/civilization/edit/init";
const url_civilization_edit_save = doamin + "/civilization/edit/save";
const url_nation_list = doamin + "/nation/list";
const url_nation_add = doamin + "/nation/save";
const url_nation_edit_init = doamin + "/nation/edit/init";
const url_nation_edit_save = doamin + "/nation/edit/save";
const url_continent_add = doamin + "/continent/save";

const url_search_nation = doamin + "/nation/search";
const url_search_civilization = doamin + "/civilization/search";
const url_search_continent = doamin + "/continent/search";

export default {
  table_page_node, //
  table_size_node, //
  doamin,
  url_node_list,
  url_node_add,
  url_node_edit_init,
  url_node_edit_save,
  url_civilization_list,
  url_civilization_add,
  url_civilization_edit_init,
  url_civilization_edit_save,
  url_nation_list,
  url_nation_add,
  url_nation_edit_init,
  url_nation_edit_save,
  url_search_nation,
  url_search_civilization,
  url_search_continent
};
</script>

